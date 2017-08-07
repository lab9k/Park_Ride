package api;

import org.joda.time.DateTime;

public interface IDistanceApi {

    DistanceApiRequest createDistanceApiRequest(String start, String end, DateTime departureTime);

}
