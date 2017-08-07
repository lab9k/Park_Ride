package api;

import api.callbacks.DistanceApiCallback;
import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.PendingResult;
import com.google.maps.PendingResult.Callback;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.TransitMode;
import com.google.maps.model.TravelMode;
import domain.ParkAndRide;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.joda.time.DateTime;
import org.springframework.scheduling.annotation.Async;

public class DistanceApiRequest implements PendingResult<List<ParkAndRideTime>> {

    private CountDownLatch latch = new CountDownLatch(1);

    private final DistanceMatrixApiRequest distanceStartToParkAndRide;

    private final List<ParkAndRide> parkAndRides;

    private final String[] destinationAdress;
    private final GeoApiContext context;
    private final String[] parkAndRideCoordinates;
    private final DateTime departureTime;
    private final List<ParkAndRideTime> parkAndRideTimes = Collections.synchronizedList(new ArrayList<>());


    public DistanceApiRequest(String startLocationId, String endLocationId, DateTime departureTime,
            List<ParkAndRide> parkAndRides, String[] parkAndRideCoordinates, GeoApiContext geoApiContext) {
        this.parkAndRides = parkAndRides;
        this.context = geoApiContext;
        this.departureTime = departureTime;
        String[] origin = {"place_id:"+startLocationId};
        String[] destination = {"place_id:"+endLocationId};
        this.destinationAdress = destination;
        this.parkAndRideCoordinates = parkAndRideCoordinates;
        distanceStartToParkAndRide = DistanceMatrixApi
                                    .getDistanceMatrix(geoApiContext, origin, parkAndRideCoordinates)
                                    .departureTime(departureTime)
                                    .language("nl");
    }

    @Async
    @Override
    public void setCallback(Callback<List<ParkAndRideTime>> clbck) {
        distanceStartToParkAndRide.setCallback(new Callback<DistanceMatrix>() {
            @Override
            public void onResult(DistanceMatrix distanceMatrix) {
                for(int i = 0; i < distanceMatrix.destinationAddresses.length; i++) {
                    ParkAndRideTime parkAndRideTime = new ParkAndRideTime();
                    parkAndRideTime.parkAndRide = parkAndRides.get(i);
                    parkAndRideTime.totalTime = distanceMatrix.rows[0].elements[i].duration.inSeconds;
                    parkAndRideTime.timeToPR = distanceMatrix.rows[0].elements[i].duration.inSeconds;
                    parkAndRideTimes.add(parkAndRideTime);
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
            latch = new CountDownLatch(parkAndRideCoordinates.length);
            for(int i = 0; i < parkAndRideTimes.size(); i++) {
                String[] origin = {parkAndRideCoordinates[i]};
                DateTime departureTimeFromPr = departureTime.plusSeconds((int) parkAndRideTimes.get(i).totalTime);

                DistanceMatrixApiRequest parkAndRideToEndRquest = DistanceMatrixApi.
                                                                    getDistanceMatrix(context, origin, destinationAdress)
                                                                    .language("nl")
                                                                    .departureTime(departureTimeFromPr)
                                                                    .mode(TravelMode.TRANSIT)
                                                                    .transitModes(TransitMode.BUS, TransitMode.TRAM);


                parkAndRideToEndRquest.setCallback(new DistanceApiCallback(parkAndRideTimes.get(i),clbck,
                        (DistanceMatrix distanceMatrix, ParkAndRideTime parkAndRideTime) -> {
                            parkAndRideTime.totalTime += distanceMatrix.rows[0].elements[0].duration.inSeconds;
                            latch.countDown();
                }));
            }
            latch.await();
            parkAndRideTimes.sort((ParkAndRideTime p1, ParkAndRideTime p2) -> {
                return ((Double) p1.totalTime).compareTo((Double) p2.totalTime);
            });

            parkAndRideTimes.sort((ParkAndRideTime p1, ParkAndRideTime p2) -> {
                return ((Double) p1.totalTime).compareTo((Double) p2.totalTime);
            });
            clbck.onResult(parkAndRideTimes.stream().limit(3).collect(Collectors.toList()));
        } catch (InterruptedException ex) {
            Logger.getLogger(DistanceApiRequest.class.getName()).log(Level.SEVERE, null, ex);
            throw new IllegalStateException(ex);
        }



    }

    @Override
    public List<ParkAndRideTime> await() throws Exception {
        throw new UnsupportedOperationException("This operation is not supported");
    }

    @Override
    public List<ParkAndRideTime> awaitIgnoreError() {
        throw new UnsupportedOperationException("This operation is not supported");
    }

    @Override
    public void cancel() {
        distanceStartToParkAndRide.cancel();
    }

}
