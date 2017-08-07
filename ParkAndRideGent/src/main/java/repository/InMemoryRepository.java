package repository;

import domain.Location;
import domain.ParkAndRide;
import domain.ParkAndRideRate;
import domain.Route;
import domain.TicketType;
import java.math.BigDecimal;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class InMemoryRepository implements Repository {

    private final List<ParkAndRide> parkAndRides;

    public InMemoryRepository() {
        this.parkAndRides = new ArrayList<>();
        ParkAndRideRate gspRate1 = new ParkAndRideRate(BigDecimal.valueOf(125), TicketType.SEASON_TICKET, Period.ofMonths(3));
        ParkAndRideRate gspRate2 = new ParkAndRideRate(BigDecimal.valueOf(500), TicketType.SEASON_TICKET, Period.ofMonths(12));

        ParkAndRideRate galRate1 = new ParkAndRideRate(BigDecimal.valueOf(2), TicketType.DAY_TICKET, Period.ofDays(1));
        ParkAndRideRate galRate2 = new ParkAndRideRate(BigDecimal.valueOf(100), TicketType.SEASON_TICKET, Period.ofMonths(12));
        ParkAndRideRate galRate3 = new ParkAndRideRate(BigDecimal.valueOf(160), TicketType.SEASON_TICKET, Period.ofMonths(12));

        galRate2.setNotes("Zonder toegang tot fietsenstalling");
        galRate3.setNotes("Met toegang tot fietsenstalling");

        List<ParkAndRideRate> gspRates = new ArrayList<>();
        gspRates.add(gspRate1);
        gspRates.add(gspRate2);

        List<ParkAndRideRate> galRates = new ArrayList<>();
        galRates.add(galRate1);
        galRates.add(galRate2);
        galRates.add(galRate3);
        parkAndRides.add(new ParkAndRide(1, "Galveston", "galveston", 250, true, true, new Location(51.074804, 3.721892)));
        parkAndRides.add(new ParkAndRide(2, "Muide", "muide", 90, false, true, new Location(51.073585, 3.734766)));
        parkAndRides.add(new ParkAndRide(3, "Vliegtuiglaan", null, 80, false, false, new Location(51.07425, 3.74309)));
        parkAndRides.add(new ParkAndRide(4, "Oostakker", "oostakker", 200, false, false, new Location(51.074341, 3.777314)));
        parkAndRides.add(new ParkAndRide(5, "Rozebroeken", null, 90, false, true, new Location(51.060362, 3.762537)));
        //6
        parkAndRides.add(new ParkAndRide(7, "Bellevue", null, 150, false, true, new Location(51.03614, 3.734155)));
        parkAndRides.add(new ParkAndRide(8, "Gentbrugge", "gentbrugge", 250, false, true, new Location(51.035663, 3.757684)));
        parkAndRides.add(new ParkAndRide(9, "Gentbrugge Arsenaal", "gentbrugge-arsenaal", 250, false, false, new Location(51.032564, 3.758556)));
        parkAndRides.add(new ParkAndRide(10, "Moscou", "moscou", 15, false, false, new Location(51.029502, 3.750708)));
        parkAndRides.add(new ParkAndRide(11, "Maaltepark", "maaltepark", 40, false, true, new Location(51.022986, 3.703132)));
        parkAndRides.add(new ParkAndRide(12, "Hekers", "hekers", 68, false, false, new Location(51.002791, 3.710432)));
        parkAndRides.add(new ParkAndRide(13, "The Loop-Expo", "loopexpo", 168, false, true, new Location(51.02469, 3.69547)));
        parkAndRides.add(new ParkAndRide(14, "Sint-Pietersstation", "sint-pietersstation", 300, true, true, new Location(51.036914, 3.704667)));
        //15
        //16
        parkAndRides.add(new ParkAndRide(17, "Watersportbaan", "watersportbaan", 230, false, true, new Location(51.04986, 3.686305)));
        //18
        //19
        parkAndRides.add(new ParkAndRide(20, "Bourgoyen", "bourgoyen", 250, false, true, new Location(51.067404, 3.681643)));
        parkAndRides.add(new ParkAndRide(21, "Mariakerke Post", "mariakerke-post", 25, false, false, new Location(51.076371, 3.67827)));
        parkAndRides.add(new ParkAndRide(22, "Neptunus", "neptunus-wondelgem", 148, false, false, new Location(51.088229, 3.703255)));
        //23
        parkAndRides.add(new ParkAndRide(24, "Industrieweg", "industrieweg-wondelgem", 46, false, false, new Location(51.098481, 3.711486)));
        parkAndRides.get(0).setRates(galRates);
        parkAndRides.get(12).setRates(gspRates);
    }

    @Override
    public Location getCity() {
        return new Location(51.0524519, 3.7146473);
    }

    @Override
    public List<ParkAndRide> getParkAndRides() {
        return parkAndRides;
    }

    @Override
    public String[] getParkAndRideCoordinates() {
        return getParkAndRides()
                .stream()
                .map(parkAndRide -> parkAndRide.getLocation().getFormattedCoordinates())
                .toArray(size -> new String[size]);
    }

    @Override
    public ParkAndRide getParkAndRide(int id) {
         return parkAndRides.stream().filter(pr -> pr.getId() == id).findFirst().get();
    }

}
