package domain;

import java.util.Objects;

public class Location {

    //<editor-fold defaultstate="expanded" desc="Attributes">
    private double latitude;
    private double longitude;
    private String name;
    //</editor-fold>

    //<editor-fold defaultstate="expanded" desc="Constructors">
    public Location(double latitude, double longitude) {
        this(latitude, longitude, "");
    }

    public Location(double latitude, double longitude, String name) {
        setLatitude(latitude);
        setLongitude(longitude);
        setName(name);
    }
    //</editor-fold>

    //<editor-fold defaultstate="expanded" desc="Getters">
    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getName() {
        return name;
    }
    //</editor-fold>

    //<editor-fold defaultstate="expanded" desc="Setters">
    public void setLatitude(double latitude) {
        if(latitude < -90 || latitude > 90)
            throw new IllegalArgumentException("Location latitude should be between -90 and 90");

        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        if(longitude < -180 || longitude > 180)
            throw new IllegalArgumentException("Location longitude should be between -180 and 180");
        this.longitude = longitude;
    }

    public void setName(String name) {
        if(name == null)
            throw new IllegalArgumentException("Location name cannot be null");
        this.name = name;
    }
    //</editor-fold>

    //<editor-fold defaultstate="expanded" desc="Other Methods">
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + (int) (Double.doubleToLongBits(this.latitude) ^ (Double.doubleToLongBits(this.latitude) >>> 32));
        hash = 41 * hash + (int) (Double.doubleToLongBits(this.longitude) ^ (Double.doubleToLongBits(this.longitude) >>> 32));
        hash = 41 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Location other = (Location) obj;
        if (Double.doubleToLongBits(this.latitude) != Double.doubleToLongBits(other.latitude)) {
            return false;
        }
        if (Double.doubleToLongBits(this.longitude) != Double.doubleToLongBits(other.longitude)) {
            return false;
        }
        return true;
    }

    public String getFormattedCoordinates() {
        return latitude + "," + longitude;
    }
    //</editor-fold>

}