package api;

import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.PendingResult;
import com.google.maps.PendingResult.Callback;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.DirectionsStep;
import com.google.maps.model.LatLng;
import com.google.maps.model.TravelMode;
import com.google.maps.model.VehicleType;
import domain.BusSubroute;
import domain.CarSubroute;
import domain.Location;
import domain.Route;
import domain.Subroute;
import domain.TramSubroute;
import domain.WalkSubroute;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.joda.time.DateTime;
import org.springframework.scheduling.annotation.Async;
import util.Utils;

public class RouteApiRequest implements PendingResult<Route>{

    private final CountDownLatch latch = new CountDownLatch(2);

    private final DirectionsApiRequest directionsStartToParkAndRide;
    private final DirectionsApiRequest directionsParkAndRideToEnd;

    private final List<Subroute> unsortedSubroutes = Collections.synchronizedList(new ArrayList<>());
    private final ParkAndRideTime parkAndRideTime;
    private final int rank;

    private final DateTime startTime;
    private final DateTime startTimeFromPR;

    public RouteApiRequest(String start, String end, DateTime startTime, ParkAndRideTime via, int rank, GeoApiContext context) {
        String origin = "place_id:"+start;
        String destination = "place_id:"+end;
        this.parkAndRideTime = via;
        this.startTime = startTime;
        this.startTimeFromPR = startTime.plusSeconds((int) via.timeToPR);
        this.rank = rank;

        this.directionsStartToParkAndRide = DirectionsApi.newRequest(context)
                                            .origin(origin)
                                            .departureTime(startTime)
                                            .destination(
                                                new LatLng(
                                                    via.parkAndRide.getLocation().getLatitude(),
                                                    via.parkAndRide.getLocation().getLongitude()))
                                            .mode(TravelMode.DRIVING)
                                            .language("nl");

        this.directionsParkAndRideToEnd =   DirectionsApi.newRequest(context)
                                            .origin(
                                                new LatLng(
                                                    via.parkAndRide.getLocation().getLatitude(),
                                                    via.parkAndRide.getLocation().getLongitude()))
                                            .destination(destination)
                                            .departureTime(startTimeFromPR)
                                            .mode(TravelMode.TRANSIT)
                                            .language("nl");

    }

    @Async
    @Override
    public void setCallback(Callback<Route> clbck) {

        directionsStartToParkAndRide.setCallback(new Callback<DirectionsResult>() {
            @Override
            public void onResult(DirectionsResult startToParkAndRideResult) {
                // first route of first leg for start location and start time
                DirectionsLeg start = startToParkAndRideResult.routes[0].legs[0];

                Location startLocation = new Location(start.startLocation.lat,
                                                      start.startLocation.lng,
                                                      start.startAddress);
                // last leg of last route for end location and end time
                DirectionsLeg[] l = startToParkAndRideResult.routes[startToParkAndRideResult.routes.length-1].legs;
                DirectionsLeg end = l[l.length-1];

                Location endLocation = new Location(end.endLocation.lat,
                                                    end.endLocation.lng,
                                                    end.endAddress);

                double distanceInMeters = 0;
                ArrayList polyLines = new ArrayList<>();

                for (DirectionsRoute route1 : startToParkAndRideResult.routes) {
                    for (DirectionsLeg leg : route1.legs) {
                        distanceInMeters += leg.distance.inMeters;
                        for(DirectionsStep step: leg.steps)
                            polyLines.add(step.polyline.getEncodedPath().replace("\\", "\\\\"));
                    }
                }
                CarSubroute cs = new CarSubroute(
                        Utils.dateTimeToLocalDateTime(startTime),
                        Utils.dateTimeToLocalDateTime(startTimeFromPR),
                        startLocation, endLocation,
                        "Rij naar de P+R", distanceInMeters, polyLines);
                unsortedSubroutes.add(cs);

                latch.countDown();
            }

            @Override
            public void onFailure(Throwable thrwbl) {
                clbck.onFailure(thrwbl);
            }
        });

        directionsParkAndRideToEnd.setCallback(new Callback<DirectionsResult>() {
            @Override
            public void onResult(DirectionsResult parkAndRideToEndResult) {

                DirectionsStep[] steps = parkAndRideToEndResult.routes[0].legs[0].steps;
                LocalDateTime departureDateTime = Utils.dateTimeToLocalDateTime(startTimeFromPR);

                for (DirectionsStep step : steps) {
                    LatLng start = step.startLocation;
                    Location startLocation = new Location(start.lat, start.lng);
                    LatLng end = step.endLocation;
                    Location endLocation = new Location(end.lat, end.lng);
                    List<String> stops = new ArrayList<String>() {{
                        add("");
                    }};

                    ArrayList polyLines = new ArrayList<>();

                    Subroute subroute = null;
                    // First check if travelmode is TRANSIT or WALKING
                    if (step.travelMode  == TravelMode.WALKING) {

                        for(DirectionsStep detailStep: step.steps) {
                            polyLines.add(detailStep.polyline.getEncodedPath().replace("\\", "\\\\"));
                        }

                        subroute = new WalkSubroute(departureDateTime,
                                departureDateTime.plusSeconds(step.duration.inSeconds), startLocation, endLocation, step.htmlInstructions, step.distance.inMeters, polyLines);
                        departureDateTime = departureDateTime.plusSeconds(step.duration.inSeconds);
                    }
                    else if (step.travelMode  == TravelMode.TRANSIT) {

                        polyLines.add(step.polyline.getEncodedPath().replace("\\", "\\\\"));

                        if(VehicleType.BUS == step.transitDetails.line.vehicle.type){
                            subroute = new BusSubroute(Utils.dateTimeToLocalDateTime(step.transitDetails.departureTime), Utils.dateTimeToLocalDateTime(step.transitDetails.arrivalTime), startLocation, endLocation, step.htmlInstructions, step.distance.inMeters, BigDecimal.valueOf(2), step.transitDetails.headsign, step.transitDetails.line.shortName, step.transitDetails.line.color, 1,
                                LocalDateTime.MAX, stops, polyLines);
                        }
                        else if (VehicleType.TRAM == step.transitDetails.line.vehicle.type) {
                           subroute = new TramSubroute(Utils.dateTimeToLocalDateTime(step.transitDetails.departureTime), Utils.dateTimeToLocalDateTime(step.transitDetails.arrivalTime), startLocation, endLocation, step.htmlInstructions, (double)step.distance.inMeters, BigDecimal.valueOf(2), step.transitDetails.headsign, step.transitDetails.line.shortName, step.transitDetails.line.color, 1,
                                   LocalDateTime.MAX, stops, polyLines);
                        }

                        departureDateTime = Utils.dateTimeToLocalDateTime(step.transitDetails.arrivalTime);
                   }

                   if(subroute != null) {
                        unsortedSubroutes.add(subroute);
                   }

                }
                latch.countDown();
            }

            @Override
            public void onFailure(Throwable thrwbl) {
                clbck.onFailure(thrwbl);
            }
        });

        try {
            latch.await();

            Route route = new Route(parkAndRideTime.parkAndRide, unsortedSubroutes);
            route.setRank(rank);
            clbck.onResult(route);
        } catch (InterruptedException ex) {
            Logger.getLogger(RouteApiRequest.class.getName()).log(Level.SEVERE, null, ex);
            throw new IllegalStateException(ex);
        }


    }

    @Override
    public Route await() throws Exception {
        directionsParkAndRideToEnd.await();
        directionsStartToParkAndRide.await();
        return new Route(parkAndRideTime.parkAndRide, unsortedSubroutes);
    }

    @Override
    public Route awaitIgnoreError() {
        try {
            directionsParkAndRideToEnd.await();
            directionsStartToParkAndRide.await();
            return new Route(parkAndRideTime.parkAndRide, unsortedSubroutes);
        } catch (Exception ex) {
            Logger.getLogger(RouteApiRequest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void cancel() {
        directionsParkAndRideToEnd.cancel();
        directionsStartToParkAndRide.cancel();
    }




}
