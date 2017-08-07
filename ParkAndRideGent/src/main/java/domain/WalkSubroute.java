package domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class WalkSubroute extends Subroute {

    //<editor-fold defaultstate="expanded" desc="Attributes">
    //</editor-fold>

    //<editor-fold defaultstate="expanded" desc="Constructors">
    public WalkSubroute(LocalDateTime startTime, LocalDateTime endTime, Location startLocation, Location endLocation, String text, double length, ArrayList<String> encodedPolyline) {
        super(startTime, endTime, startLocation, endLocation, text, length, BigDecimal.ZERO, encodedPolyline);
    }
    //</editor-fold>

    //<editor-fold defaultstate="expanded" desc="Getters">
    public String getRouteType() {
        return "walking";
    }
    //</editor-fold>

    //<editor-fold defaultstate="expanded" desc="Setters">
    //</editor-fold>

    //<editor-fold defaultstate="expanded" desc="Other Methods">
    //</editor-fold>

}