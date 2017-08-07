package viewmodel;

import domain.ParkAndRide;
import domain.Route;

public class DistanceViewModel {

    //<editor-fold defaultstate="expanded" desc="Attributes">
    private String start;
    private String end;
    private double duration;
    private double durationToPr;
    private long distance;
    private String durationText;
    private ParkAndRide via;
    private Route route;
    //</editor-fold>

    //<editor-fold defaultstate="expanded" desc="Constructors">
    public DistanceViewModel(String start, String end, double duration, double durationToPr, long distance, String durationText) {
        this.start = start;
        this.end = end;
        this.duration = duration;
        this.durationToPr = durationToPr;
        this.distance = distance;
        this.durationText = durationText;
    }
    //</editor-fold>

    //<editor-fold defaultstate="expanded" desc="Getters">
    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public double getDuration() {
        return duration;
    }

    public long getDistance() {
        return distance;
    }

    public String getDurationText() {
        return durationText;
    }

    public ParkAndRide getVia() {
        return via;
    }

    public Route getRoute() {
        return route;
    }

    public double getDurationToPr() {
        return durationToPr;
    }
    //</editor-fold>

    //<editor-fold defaultstate="expanded" desc="Setters">
    public void setStart(String start) {
        this.start = start;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public void setDistance(long distance) {
        this.distance = distance;
    }

    public void setDurationText(String durationText) {
        this.durationText = durationText;
    }

    public void setVia(ParkAndRide via) {
        this.via = via;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public void setDurationToPr(double durationToPr) {
        this.durationToPr = durationToPr;
    }
    //</editor-fold>

    //<editor-fold defaultstate="expanded" desc="Other Methods">
    //</editor-fold>

}
