package domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class PublicTransportSubroute extends Subroute {

    //<editor-fold defaultstate="expanded" desc="Attributes">
    private String name;
    private String number;
    private String color;
    private double frequency;
    private LocalDateTime lastReturn;
    private List<String> stops;
    //</editor-fold>

    //<editor-fold defaultstate="expanded" desc="Constructors">
    public PublicTransportSubroute(LocalDateTime startTime, LocalDateTime endTime, Location startLocation, Location endLocation, String text, double length, BigDecimal price,
            String name, String number, String color, double frequency, LocalDateTime lastReturn, List<String> stops, ArrayList<String> encodedPolyline) {
        super(startTime, endTime, startLocation, endLocation, text, length, price, encodedPolyline);

        setName(name);
        setNumber(number);
        setColor(color);
        setFrequency(frequency);
        setLastReturn(lastReturn);
        setStops(stops);
    }



    //</editor-fold>

    //<editor-fold defaultstate="expanded" desc="Getters">
    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getColor() {
        return color;
    }

    public double getFrequency() {
        return frequency;
    }

    public LocalDateTime getLastReturn() {
        return lastReturn;
    }

    public List<String> getStops() {
        return stops;
    }

    @Override //Public transport can't add start/end time based on external delay
    public LocalDateTime getEndTime() {
        return endTime;
    }

    @Override //Public transport can't add start/end time based on external delay
    public LocalDateTime getStartTime() {
        return startTime;
    }

    @Override
    public String getText() {
        return super.getText() + " (Lijn " + number + ")";
    }

    //</editor-fold>

    //<editor-fold defaultstate="expanded" desc="Setters">
    public void setName(String name) {
        if (name == null || name.equals("")) {
            throw new IllegalArgumentException("Name of a publictransportsubroute can not be empty or null");
        }

        this.name = name;
    }

    public void setNumber(String number) {
        if (number == null || "".equals(number)) {
            throw new IllegalArgumentException("Number can not be empty");
        }

        this.number = number;
    }

    public void setColor(String color) {
        if (color == null || "".equals(color)) {
            throw new IllegalArgumentException("Color can not be empty");
        }

        this.color = color;
    }

    public void setFrequency(double frequency) {
        if (frequency <= 0) {
            throw new IllegalArgumentException("Frequency of a publictransportroute must be positive");
        }

        this.frequency = frequency;
    }

    public void setLastReturn(LocalDateTime lastReturn) {
        if (lastReturn == null) {
            throw new IllegalArgumentException("Last return of a publictransportroute can not be null");
        }

        if (lastReturn.toLocalDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Last return of a publictransportroute must be before today");
        }

        this.lastReturn = lastReturn;
    }

    public void setStops(List<String> stops) {
        if (stops == null || stops.isEmpty()) {
            throw new IllegalArgumentException("List of stops of a publictransportroute can not be null or empty");
        }

        this.stops = stops;
    }
    //</editor-fold>

    //<editor-fold defaultstate="expanded" desc="Other Methods">

    @Override
    public Boolean isAchievable() {
        return getStartTime().compareTo(precedingSubroute.getEndTime()) >= 0;
    }

    //</editor-fold>

}
