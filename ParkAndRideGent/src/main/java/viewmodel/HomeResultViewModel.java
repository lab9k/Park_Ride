package viewmodel;

import com.google.maps.model.DistanceMatrix;
import domain.ParkAndRide;
import java.util.List;

public class HomeResultViewModel {

    //<editor-fold defaultstate="expanded" desc="Attributes">
    private String startLocationName;
    private String endLocationName;
    private final List<DistanceViewModel> distanceViewModels;
    //</editor-fold>

    //<editor-fold defaultstate="expanded" desc="Constructors">
    public HomeResultViewModel(DistanceMatrix fromStartToPr, List<DistanceMatrix> fromPrToEnd, List<ParkAndRide> parkAndRides) {
        // convert distancematrix to list of viewmodels, one for each route
        distanceViewModels = DistanceViewModelFactory.createDistanceViewModels(fromStartToPr);
        startLocationName = fromStartToPr.originAddresses[0];
        endLocationName = fromPrToEnd.get(0).destinationAddresses[0];

        for (int i = 0; i < distanceViewModels.size(); i++) {
            // get corresponding distancematrix from fromPrToEnd list
            // end is currently filled in with the park and ride because these are the
            // distanceviewmodels of start to pr
            String name = distanceViewModels.get(i).getEnd();
            DistanceMatrix matrix = fromPrToEnd.stream().filter(t -> t.originAddresses[0].equals(name))
                    .findFirst().get();

            distanceViewModels.get(i).setDistance(matrix.rows[0].elements[0].distance.inMeters + distanceViewModels.get(i).getDistance());
            distanceViewModels.get(i).setDuration(matrix.rows[0].elements[0].duration.inSeconds + distanceViewModels.get(i).getDuration());
            // find the park and ride using the current end
            distanceViewModels.get(i).setVia(parkAndRides.stream().filter(pr -> pr.getName().equals(name)).findFirst().get());
            // set end to actual destination
            distanceViewModels.get(i).setEnd(matrix.destinationAddresses[0]);
        }

        distanceViewModels.sort((DistanceViewModel o1, DistanceViewModel o2) -> {
            return (int) (o1.getDuration() - o2.getDuration());
        });
    }
    //</editor-fold>

    //<editor-fold defaultstate="expanded" desc="Getters">
    public List<DistanceViewModel> getDvms() {
        return distanceViewModels;
    }

    public String getStartLocationName() {
        return startLocationName;
    }

    public String getEndLocationName() {
        return endLocationName;
    }
    //</editor-fold>

    //<editor-fold defaultstate="expanded" desc="Setters">
    public void setPrtoend(List<DistanceViewModel> prtoend) {
        //this.parkAndRideToEnd = prtoend;
    }

    public void setStartLocationName(String startLocatoinName) {
        this.startLocationName = startLocatoinName;
    }

    public void setEndLocationName(String endLocationName) {
        this.endLocationName = endLocationName;
    }

    public void sortTopList() {
        distanceViewModels.subList(0, 3).sort((DistanceViewModel o1, DistanceViewModel o2) -> {
            return (int) (o1.getRoute().getTripDuration() - o2.getRoute().getTripDuration());
        });
    }
    //</editor-fold>
    //<editor-fold defaultstate="expanded" desc="Other Methods">
    //</editor-fold>
}
