package domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;

public final class Route {

    //<editor-fold defaultstate="expanded" desc="Attributes">
    private ParkAndRide parkAndRide;
    private final List<Subroute> subroutes;

    private int rank;
    //</editor-fold>

    //<editor-fold defaultstate="expanded" desc="Constructors">
    public Route(ParkAndRide parkAndRide, List<Subroute> subroutes) {
        setParkAndRide(parkAndRide);
        this.subroutes = new LinkedList<>();

        subroutes
                .sort((Subroute sub, Subroute sub2) -> sub.getStartTime().compareTo(sub2.getStartTime()));
        int totalDelayInMinutes = 0;

        Subroute previousSubroute = null;

        for(Subroute subroute: subroutes) {
            subroute.setPrecedingSubroute(previousSubroute);
            this.subroutes.add(subroute);
            previousSubroute = subroute;
        }


    }
    //</editor-fold>

    //<editor-fold defaultstate="expanded" desc="Getters">
    public ParkAndRide getParkAndRide() {
        return parkAndRide;
    }

    public List<Subroute> getSubroutes() {
        return subroutes;
    }

    public int getRank() {
        return rank;
    }
    //</editor-fold>

    //<editor-fold defaultstate="expanded" desc="Setters">
    public void setParkAndRide(ParkAndRide parkAndRide) {
        if (parkAndRide == null) {
            throw new IllegalArgumentException("Park and Ride of a route can not be null");
        }
        this.parkAndRide = parkAndRide;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    //</editor-fold>

    //<editor-fold defaultstate="expanded" desc="Other Methods">

    public void addSubroute(Subroute subroute) {
        subroutes.add(subroute);
    }

    public BigDecimal getPrice() {
        return subroutes
                .stream()
                .map(subroute -> subroute.getPrice())
                .reduce(BigDecimal.ZERO, (a, b) -> a.add(b));
    }

    public int getTotalCarTime() {
        int time = 0;
        for (Subroute sub : subroutes) {
            if (sub.getRouteType() == "car") {
                time += sub.getDurationInMinutes();
            }
        }
        return time;
    }

    public int getTotalBusTime() {
        int time = 0;
        for (Subroute sub : subroutes) {
            if (sub.getRouteType() == "bus") {
                time += sub.getDurationInMinutes();
            }
        }
        return time;
    }

    public int getTotalTramTime() {
        int time = 0;
        for (Subroute sub : subroutes) {
            if (sub.getRouteType() == "tram") {
                time += sub.getDurationInMinutes();
            }
        }
        return time;
    }

    public int getTotalWalkingTime() {
        int time = 0;
        for (Subroute sub : subroutes) {
            if (sub.getRouteType() == "walking") {
                time += sub.getDurationInMinutes();
            }
        }
        return time;
    }

    public int getTotalBicycleTime() {
        int time = 0;
        for (Subroute sub : subroutes) {
            if (sub.getRouteType() == "bicycle") {
                time += sub.getDurationInMinutes();
            }
        }
        return time;
    }

    public Boolean isAchievable() {
        for(Subroute subroute : subroutes) {
            if(!subroute.isAchievable()) {
                return false;
            }
        }

        return true;
    }

    public Boolean isExpeditable() {
        return !subroutes.get(0).getStartTime().minusMinutes(30).isBefore(LocalDateTime.now());
    }
    //</editor-fold>

    public int getTripDuration() {
        LocalDateTime startTime = subroutes.get(0).getStartTime();
        LocalDateTime endTime = subroutes.get(subroutes.size() - 1).getEndTime();

        long duration = ChronoUnit.SECONDS.between(startTime, endTime);
        int minutes = (int) Math.ceil(duration / 60);
        return minutes;
    }

    public int getTotalPublicTransportTime() {
        return getTotalBusTime() + getTotalTramTime();
    }

    public int getWaitingTime() {
        int total = getTotalCarTime() + getTotalBusTime() + getTotalTramTime() + getTotalBicycleTime() + getTotalWalkingTime();
        return getTripDuration() - total;
    }

    public String durationToString(int minutes) {
        if (minutes > 59) {
            return String.format("%d u %d min", minutes / 60, minutes % 60);
        }
        return String.format("%d min", minutes);
    }

    public String getTripDurationFormatted() {
        return durationToString(getTripDuration());
    }

    public String getWaitingTimeFormatted() {
        return durationToString(getWaitingTime());
    }

    public BigDecimal getPublicTransportCost() {
        BigDecimal price = BigDecimal.ZERO;
        for (Subroute sub : subroutes) {
            if (sub.getRouteType() == "bus" || sub.getRouteType() == "tram") {
                price = sub.getPrice();
            }
        }
        return price;
    }

    public String getParkAndRideCost() {
        if (getParkAndRide().getPaid()) {
            if (getParkAndRide().getRates().get(0).getTicketType() == TicketType.DAY_TICKET) {
                return "P+R: Dagticket €" + getParkAndRide().getRates().get(0).getPrice();
            } else {
                return "P+R: Abonnement vereist!";
            }
        }
        return "P+R: Gratis";
    }
    //</editor-fold>
}
