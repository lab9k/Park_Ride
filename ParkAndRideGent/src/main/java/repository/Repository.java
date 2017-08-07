package repository;

import domain.Location;
import domain.ParkAndRide;
import java.util.List;

public interface Repository {

    Location getCity();

    List<ParkAndRide> getParkAndRides();

    String[] getParkAndRideCoordinates();

     ParkAndRide getParkAndRide(int id);
}
