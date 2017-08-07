package viewmodel;

import com.google.maps.model.DistanceMatrix;
import java.util.ArrayList;
import java.util.List;

public class DistanceViewModelFactory {

    private DistanceViewModelFactory() {
    }

    public static List<DistanceViewModel> createDistanceViewModels(DistanceMatrix distanceMatrix) {
        List<DistanceViewModel> distanceViewModels = new ArrayList<>();
        for(int j = 0; j < distanceMatrix.originAddresses.length; j++) {
            String start = distanceMatrix.originAddresses[j];
            for(int i = 0; i < distanceMatrix.destinationAddresses.length; i++) {
                String dest = distanceMatrix.destinationAddresses[i];
                long distance = distanceMatrix.rows[j].elements[i].distance.inMeters;
                long duration = distanceMatrix.rows[j].elements[i].duration.inSeconds;
                long durationToPr = distanceMatrix.rows[j].elements[i].duration.inSeconds;
                String durationText = distanceMatrix.rows[j].elements[i].duration.humanReadable;
                distanceViewModels.add(new DistanceViewModel(start, dest, duration, durationToPr, distance, durationText));
            }
        }
        return distanceViewModels;
    }

}
