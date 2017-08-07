package domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

public class RouteTest {

    private Route route;

    @Before
    public void before() {
        //SETUP
        Subroute sr1 = mock(Subroute.class);

        when(sr1.getPrice()).thenReturn(BigDecimal.TEN);
        when(sr1.getStartTime()).thenReturn(LocalDateTime.of(LocalDate.now(), LocalTime.of(3, 12)));
        when(sr1.getRouteType()).thenReturn("car");
        when(sr1.getDurationInMinutes()).thenReturn(40);

        Subroute sr2 = mock(Subroute.class);
        when(sr2.getPrice()).thenReturn(new BigDecimal(2));
        when(sr2.getStartTime()).thenReturn(LocalDateTime.now().plusMinutes(30));
        when(sr2.getEndTime()).thenReturn(LocalDateTime.of(LocalDate.now(), LocalTime.of(4, 7)));
        when(sr2.getRouteType()).thenReturn("bus");
        when(sr2.getDurationInMinutes()).thenReturn(10);

        ParkAndRide pr1 = mock(ParkAndRide.class);
        List<Subroute> routes = new ArrayList<>();
        routes.add(sr1);
        routes.add(sr2);
        route = new Route(pr1, routes);

    }

    @Test
    public void testRouteGetPriceShouldReturnTheTotalPriceOfTheSubroutes() {
        //ACT
        BigDecimal resultExpected = new BigDecimal(12);
        BigDecimal resultActual = route.getPrice();

        //ASSERT
        Assert.assertEquals(resultExpected, resultActual);
    }

    @Test
    public void testGetTripDuration() {
        int resultExpected = 55;
        int resultActual = route.getTripDuration();

        Assert.assertEquals(resultExpected, resultActual);
    }

    @Test
    public void testGetTotalPublicTransportTime() {
        int resultExpected = 10;
        int resultActual = route.getTotalPublicTransportTime();

        Assert.assertEquals(resultExpected, resultActual);
    }

    @Test
    public void testGetWaitingTime() {
        int resultExpected = 5;
        int resultActual = route.getWaitingTime();

        Assert.assertEquals(resultExpected, resultActual);
    }

    @Test
    public void testGetPublicTransportCost() {
        BigDecimal resultExpected = new BigDecimal(2);
        BigDecimal resultActual = route.getPublicTransportCost();

        Assert.assertEquals(resultExpected, resultActual);
    }
}
