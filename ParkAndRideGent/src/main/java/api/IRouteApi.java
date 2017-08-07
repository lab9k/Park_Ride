package api;

import domain.ParkAndRide;
import domain.Route;
import java.util.List;
import java.util.function.Consumer;
import org.joda.time.DateTime;

public interface IRouteApi {

    void getRoute(String start, String end, DateTime startTime, ParkAndRideTime via, int rank, Consumer<Route> consumer);
    void getRouteVia(String start, String end, DateTime startTime, ParkAndRide via, int rank, Consumer<Route> consumer);
    void getFastestThreeRoutes(String start, String end, DateTime startTime, Consumer<List<Route>> consumer);
}
