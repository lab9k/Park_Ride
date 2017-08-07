package api;

import com.google.maps.GeoApiContext;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.Repository;

@Service
public class DistanceApi implements IDistanceApi {

    private final Repository repository;
    private final GeoApiContext context;

    @Autowired
    public DistanceApi(Repository repository, GeoApiContext context) {
        this.repository = repository;
        this.context = context;
    }

    @Override
    public DistanceApiRequest createDistanceApiRequest(String startLocationId, String endLocationId, DateTime departureTime) {
        return new DistanceApiRequest(
                startLocationId,
                endLocationId,
                departureTime,
                repository.getParkAndRides(),
                repository.getParkAndRideCoordinates(),
                context);
    }

}
