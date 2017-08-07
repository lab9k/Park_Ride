package api.callbacks;

import api.ParkAndRideTime;
import com.google.maps.PendingResult.Callback;
import com.google.maps.model.DistanceMatrix;
import domain.ParkAndRide;
import java.util.List;
import java.util.function.BiConsumer;

public class DistanceApiCallback implements Callback<DistanceMatrix>{

    private final ParkAndRideTime parkAndRide;
    private final BiConsumer<DistanceMatrix, ParkAndRideTime> onResult;
    private final Callback<List<ParkAndRideTime>> clbk;


    public DistanceApiCallback(ParkAndRideTime parkAndRide, Callback<List<ParkAndRideTime>> clbck, BiConsumer<DistanceMatrix, ParkAndRideTime> onResult) {
        this.parkAndRide = parkAndRide;
        this.onResult = onResult;
        this.clbk = clbck;
    }

    @Override
    public void onResult(DistanceMatrix distanceMatrix) {
        onResult.accept(distanceMatrix, parkAndRide);
    }

    @Override
    public void onFailure(Throwable thrwbl) {
        clbk.onFailure(thrwbl);
    }

}