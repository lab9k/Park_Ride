package domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BusSubroute extends PublicTransportSubroute {

    //<editor-fold defaultstate="expanded" desc="Attributes">
    //</editor-fold>


    //<editor-fold defaultstate="expanded" desc="Constructors">

    public BusSubroute(LocalDateTime startTime, LocalDateTime endTime, Location startLocation, Location endLocation, String text, double length, BigDecimal price,
            String name, String number, String color, double frequency, LocalDateTime lastReturn, List<String> stops, ArrayList<String> encodedPolyline) {
        super(startTime, endTime, startLocation, endLocation, text, length, price, name, number, color, frequency, lastReturn, stops, encodedPolyline);
    }
    //</editor-fold>

    //<editor-fold defaultstate="expanded" desc="Getters">

    public String getRouteType() {
        return "bus";
    }
    //</editor-fold>

    //<editor-fold defaultstate="expanded" desc="Setters">
    //</editor-fold>

    //<editor-fold defaultstate="expanded" desc="Other Methods">
    //</editor-fold>

}
