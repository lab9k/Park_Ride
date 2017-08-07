package api;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.PendingResult;
import com.google.maps.model.DistanceMatrix;
import domain.ParkAndRide;
import domain.Route;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RouteApi implements IRouteApi {

    private final GeoApiContext context;
    private final IDistanceApi distanceApi;

    @Autowired
    public RouteApi(IDistanceApi distanceApi, GeoApiContext context) {
        this.context = context;
        this.distanceApi = distanceApi;
    }

    @Override
    public void getRoute(String start, String end, DateTime startTime, ParkAndRideTime parkAndRideTime, int rank, Consumer<Route> consumer) {
        new RouteApiRequest(start, end, startTime, parkAndRideTime, rank, context).setCallback(new PendingResult.Callback<Route>() {
            @Override
            public void onResult(Route route) {
                consumer.accept(route);
            }

            @Override
            public void onFailure(Throwable thrwbl) {
                System.err.println(thrwbl);
            }
        });
    }

    @Override
    public void getRouteVia(String start, String end, DateTime startTime, ParkAndRide via, int rank, Consumer<Route> consumer) {
        String[] origin = {"place_id:"+start};
        String[] parkAndRideCoordinates = {via.getLocation().getFormattedCoordinates()};

        DistanceMatrixApi.getDistanceMatrix(context, origin, parkAndRideCoordinates)
                         .departureTime(startTime)
                         .language("nl").setCallback(new PendingResult.Callback<DistanceMatrix>() {
            @Override
            public void onResult(DistanceMatrix t) {
                ParkAndRideTime parkAndRideTime = new ParkAndRideTime();
                parkAndRideTime.parkAndRide = via;
                parkAndRideTime.timeToPR = t.rows[0].elements[0].duration.inSeconds;
                getRoute(start, end, startTime, parkAndRideTime, 1, consumer);
            }

            @Override
            public void onFailure(Throwable thrwbl) {
                System.err.println(thrwbl);
            }
        });
    }


    @Override
    public void getFastestThreeRoutes(String start, String end, DateTime startTime, Consumer<List<Route>> consumer) {
        // Calculate fastest three routes
        List<Route> routes = Collections.synchronizedList(new ArrayList<>());
        distanceApi.createDistanceApiRequest(start, end, startTime).setCallback(new PendingResult.Callback<List<ParkAndRideTime>>() {
            @Override
            public void onResult(List<ParkAndRideTime> parkAndRideTimes) {
                CountDownLatch latch = new CountDownLatch(3);
                for (int i = 0; i < parkAndRideTimes.size(); i++) {
                    getRoute(start, end, startTime, parkAndRideTimes.get(i), i, (Route r) -> {
                        routes.add(r);
                        latch.countDown();
                    });
                }
                try {
                    latch.await();
                    routes.sort((Route r1, Route r2) -> {
                        return r1.getTripDuration() - r2.getTripDuration();
                    });
                    consumer.accept(routes);
                } catch (InterruptedException ex) {
                    System.err.println(ex);
                }
            }

            @Override
            public void onFailure(Throwable thrwbl) {
                System.err.println(thrwbl);
            }
        });
    }
}