package domain;

import java.util.List;

public class ParkAndRide {

    //<editor-fold defaultstate="expanded" desc="Attributes">
    private int id;
    private String name;
    private String url;
    private int totalCapacity;
    private boolean paid;
    private boolean bike;
    private Location location;
    private List<ParkAndRideRate> rates;

    public final String URL_PATH = "https://stad.gent/mobiliteit-openbare-werken/mobiliteit/mobi/parkeren/park-and-ride-pr/pr-";
    //</editor-fold>

    //<editor-fold defaultstate="expanded" desc="Constructors">
    public ParkAndRide(int id, String name, String url, int totalCapacity, boolean paid, boolean bike, Location location) {
        setId(id);
        setName(name);
        setUrl(url);
        setTotalCapacity(totalCapacity);
        setPaid(paid);
        setBike(bike);
        setLocation(location);
    }
    //</editor-fold>

    //<editor-fold defaultstate="expanded" desc="Getters">
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public int getTotalCapacity() {
        return totalCapacity;
    }

    public boolean getPaid() {
        return paid;
    }

    public boolean getBike() {
        return bike;
    }

    public Location getLocation() {
        return location;
    }

    public List<ParkAndRideRate> getRates() {
        return rates;
    }
    //</editor-fold>

    //<editor-fold defaultstate="expanded" desc="Setters">
    public void setId(int id) {
        if(id <= 0)
            throw new IllegalArgumentException("Park and Ride id must be a positive integer");

        this.id = id;
    }

    public void setName(String name) {
        if(name == null || name.equals(""))
            throw new IllegalArgumentException("Park and Ride name cannot be empty");

        this.name = name;
    }

    public void setUrl(String url) {
        if(url != null && !url.equals(""))
            this.url = URL_PATH + url;
        else
            this.url = null;
    }

    public void setTotalCapacity(int totalCapacity) {
        if(totalCapacity < 0)
            throw new IllegalArgumentException("Park and Ride capacity cannot be negative");

        this.totalCapacity = totalCapacity;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public void setBike(boolean bike) {
        this.bike = bike;
    }

    public void setLocation(Location location) {
        if(location == null)
            throw new IllegalArgumentException("Park and Ride location cannot be null");

        this.location = location;
    }

    public void setRates(List<ParkAndRideRate> rates) {
        if(!paid)
            throw new IllegalArgumentException("A free park and ride can not have rates");
        if(rates == null || rates.isEmpty())
            throw new IllegalArgumentException("Park and Ride rates can not be null or empty");

        this.rates = rates;
    }
    //</editor-fold>

    //<editor-fold defaultstate="expanded" desc="Other Methods">
    //</editor-fold>

}