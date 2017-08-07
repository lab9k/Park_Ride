package domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BicycleSubroute extends Subroute {

    //<editor-fold defaultstate="expanded" desc="Attributes">
    //</editor-fold>

    //<editor-fold defaultstate="expanded" desc="Constructors">
    public BicycleSubroute(LocalDateTime startTime, LocalDateTime endTime, Location startLocation, String text, Location endLocation, double length, ArrayList<String> encodedPolyline) {
        super(startTime, endTime, startLocation, endLocation, text, length, BigDecimal.ZERO, encodedPolyline);
    }

    public BicycleSubroute(LocalDateTime startTime, LocalDateTime endTime, Location startLocation, Location endLocation, String text, double length, BigDecimal price, ArrayList<String> encodedPolyline) {
        super(startTime, endTime, startLocation, endLocation, text, length, price, encodedPolyline);
    }
    //</editor-fold>

    //<editor-fold defaultstate="expanded" desc="Getters">
    //</editor-fold>

    //<editor-fold defaultstate="expanded" desc="Setters">
    //</editor-fold>

    //<editor-fold defaultstate="expanded" desc="Other Methods">
    //</editor-fold>

    @Override
    public String getRouteType() {
        return "bicycle";
    }

}
