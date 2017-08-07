package domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public abstract class Subroute {

    //<editor-fold defaultstate="expanded" desc="Attributes">
    protected LocalDateTime startTime;
    protected LocalDateTime endTime;
    private Location startLocation;
    private Location endLocation;
    private String text;
    private double length;
    private BigDecimal price;
    private ArrayList<String> encodedPolyline;
    protected Subroute precedingSubroute;
    private int delayInMinutes;
    //</editor-fold>

    //<editor-fold defaultstate="expanded" desc="Constructors">
    public Subroute(LocalDateTime startTime, LocalDateTime endTime, Location startLocation, Location endLocation, String text, double length, BigDecimal price, ArrayList<String> encodedPolyline) {
        setStartTime(startTime);
        setEndTime(endTime);
        setStartLocation(startLocation);
        setEndLocation(endLocation);
        setText(text);
        setLength(length);
        setPrice(price);
        setEncodedPolyline(encodedPolyline);
    }
    //</editor-fold>

    //<editor-fold defaultstate="expanded" desc="Getters">
    public LocalDateTime getStartTime() {
        return startTime.plusMinutes(getDelayWithoutThisDelay());
    }

    public String getText() {
        return text;
    }

    public LocalDateTime getEndTime() {
        return endTime.plusMinutes(getDelayWithoutThisDelay());
    }

    public Location getStartLocation() {
        return startLocation;
    }

    public Location getEndLocation() {
        return endLocation;
    }

    public double getLength() {
        return length;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ArrayList<String> getEncodedPolyline() {
        return encodedPolyline;
    }

    public abstract String getRouteType();

    public int getDelayInMinutes() {
        return delayInMinutes;
    }

    //</editor-fold>

    //<editor-fold defaultstate="expanded" desc="Setters">
    public void setStartTime(LocalDateTime startTime) {
        if (startTime == null) {
            throw new IllegalArgumentException("Start time of subroute can not be null");
        }

        if (startTime.toLocalDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Start time of subroute can not be in the past");
        }

        if (endTime != null && startTime.isAfter(endTime)) {
            throw new IllegalArgumentException("Start time of subroute should be before end time");
        }

        this.startTime = startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        if (endTime == null) {
            throw new IllegalArgumentException("End time of subroute can not be null");
        }

        if (endTime.toLocalDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("End time of subroute can not be in the past");
        }

        if (startTime != null && startTime.isAfter(endTime)) {
            throw new IllegalArgumentException("Start time of subroute should be before end time");
        }

        this.endTime = endTime;
    }

    public void setStartLocation(Location startLocation) {
        if (startLocation == null) {
            throw new IllegalArgumentException("Start location of subroute can not be null");
        }
        if (endLocation != null && startLocation.equals(endLocation)) {
            throw new IllegalArgumentException("Start location of subroute can not be equal to endlocation");
        }

        this.startLocation = startLocation;
    }

    public void setEndLocation(Location endLocation) {
        if (endLocation == null) {
            throw new IllegalArgumentException("End location of subroute can not be null");
        }
        if (startLocation != null && startLocation.equals(endLocation)) {
            throw new IllegalArgumentException("Start location of subroute can not be equal to endlocation");
        }

        this.endLocation = endLocation;
    }

    public void setLength(double length) {
        if (length <= 0) {
            throw new IllegalArgumentException("Length of a subroute must be a positive integer");
        }
        this.length = length;
    }

    public void setPrice(BigDecimal price) {
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price of a subroute can not be negative");
        }
        this.price = price;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setEncodedPolyline(ArrayList<String> encodedPolyline) {
        this.encodedPolyline = encodedPolyline;
    }

    public void setDelayInMinutes(int delayInSeconds) {
        this.delayInMinutes = delayInSeconds;
    }

    void setPrecedingSubroute(Subroute precedingSubroute) {
        this.precedingSubroute = precedingSubroute;
    }
    //</editor-fold>

    //<editor-fold defaultstate="expanded" desc="Other Methods">
    public String getFormattedStartTime() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String formattedStartTime = getStartTime().format(formatter);
        return formattedStartTime;
    }

    public String getFormattedEndTime() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String formattedEndTime = getEndTime().format(formatter);
        return formattedEndTime;
    }

    public String getFormattedTripDuration() {
        return String.valueOf(getDurationInMinutes());
    }

    public int getDurationInMinutes() {
        long duration = ChronoUnit.SECONDS.between(getStartTime(), getEndTime());
        int minutes = (int)Math.ceil(duration/60);
        return minutes;
    }

    public Boolean hasDelay() {
        return getDelayInMinutes() > 0;
    }

    public int getTotalDelayInMinutes() {

        if(this.precedingSubroute == null) {
            return 0;
        }

        return delayInMinutes + this.precedingSubroute.getTotalDelayInMinutes();
    }
    //</editor-fold>

    private int getDelayWithoutThisDelay() {
        return getTotalDelayInMinutes() - delayInMinutes;
    }
    public Boolean isAchievable() {
        return true;
    }

}
