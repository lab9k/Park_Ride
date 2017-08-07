package domain;

import java.math.BigDecimal;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class ParkAndRideTest {

    private final Location correctLocation = new Location(0, 0);

    @Test(expected = IllegalArgumentException.class)
    public void testIdNotNegative() {
        int id = -1;
        ParkAndRide instance = new ParkAndRide(id, "correctName", "correctUrl", 100, true, true, correctLocation);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIdNotZero() {
        int id = 0;
        ParkAndRide instance = new ParkAndRide(id, "correctName", "correctUrl", 100, true, true, correctLocation);
    }

    @Test
    public void testCorrectId() {
        int id = 10;
        ParkAndRide instance = new ParkAndRide(id, "correctName", "correctUrl", 100, true, true, correctLocation);
        Assert.assertEquals(id, instance.getId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNameNotNull() {
        String name = null;
        ParkAndRide instance = new ParkAndRide(1, name, "correctUrl", 100, true, true, correctLocation);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNameNotEmpty() {
        String name = "";
        ParkAndRide instance = new ParkAndRide(1, name, "correctUrl", 100, true, true, correctLocation);
    }

    @Test
    public void testCorrectName() {
        String name = "correctName";
        ParkAndRide instance = new ParkAndRide(1, name, "correctUrl", 100, true, true, correctLocation);
        Assert.assertEquals(name, instance.getName());
    }

    @Test
    public void testUrlNull() {
        String url = null;
        ParkAndRide instance = new ParkAndRide(1, "correctName", url, 100, true, true, correctLocation);
        Assert.assertNull(instance.getUrl());
    }

    @Test
    public void testUrlEmpty() {
        String url = "";
        ParkAndRide instance = new ParkAndRide(1, "correctName", url, 100, true, true, correctLocation);
        Assert.assertNull(instance.getUrl());
    }

    @Test
    public void testCorrectUrl() {
        String url = "correctUrl";
        ParkAndRide instance = new ParkAndRide(1, "correctName", url, 100, true, true, correctLocation);
        Assert.assertEquals(instance.URL_PATH + url, instance.getUrl());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCapacityNotNegative() {
        int capacity = -100;
        ParkAndRide instance = new ParkAndRide(1, "correctName", "correctUrl", capacity, true, true, correctLocation);
    }

    @Test
    public void testCapacityZero() {
        int capacity = 0;
        ParkAndRide instance = new ParkAndRide(1, "correctName", "correctUrl", capacity, true, true, correctLocation);
        Assert.assertEquals(capacity, instance.getTotalCapacity());
    }

    @Test
    public void testCorrectCapacity() {
        int capacity = 100;
        ParkAndRide instance = new ParkAndRide(1, "correctName", "correctUrl", capacity, true, true, correctLocation);
        Assert.assertEquals(capacity, instance.getTotalCapacity());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLocationNotNull() {
        Location location = null;
        ParkAndRide instance = new ParkAndRide(1, "correctName", "correctUrl", 100, true, true, location);
    }

    @Test
    public void testCorrectLocation() {
        ParkAndRide instance = new ParkAndRide(1, "correctName", "correctUrl", 100, true, true, correctLocation);
        Assert.assertEquals(correctLocation, instance.getLocation());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRatesWhenNotPaid() {
        ParkAndRideRate prr = new ParkAndRideRate(BigDecimal.ONE, TicketType.DAY_TICKET, Period.of(1, 0, 0));
        List<ParkAndRideRate> prl = new ArrayList<>();
        prl.add(prr);
        ParkAndRide instance = new ParkAndRide(1, "correctName", "correctUrl", 100, false, true, correctLocation);
        instance.setRates(prl);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRatesNull() {
        ParkAndRide instance = new ParkAndRide(1, "correctName", "correctUrl", 100, true, true, correctLocation);
        instance.setRates(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRatesEmpty() {
        List<ParkAndRideRate> prl = new ArrayList<>();
        ParkAndRide instance = new ParkAndRide(1, "correctName", "correctUrl", 100, true, true, correctLocation);
        instance.setRates(prl);
    }

    @Test
    public void testRatesCorrect() {
        ParkAndRideRate prr = new ParkAndRideRate(BigDecimal.ONE, TicketType.DAY_TICKET, Period.of(1, 0, 0));
        List<ParkAndRideRate> prl = new ArrayList<>();
        prl.add(prr);
        ParkAndRide instance = new ParkAndRide(1, "correctName", "correctUrl", 100, true, true, correctLocation);
        instance.setRates(prl);
        Assert.assertEquals(prl, instance.getRates());
    }

    @Test
    public void testPaidCorrectTrue() {
        boolean paid = true;
        ParkAndRide instance = new ParkAndRide(1, "correctName", "correctUrl", 100, paid, true, correctLocation);
        Assert.assertTrue(instance.getPaid());
    }

    @Test
    public void testPaidCorrectFalse() {
        boolean paid = false;
        ParkAndRide instance = new ParkAndRide(1, "correctName", "correctUrl", 100, paid, true, correctLocation);
        Assert.assertFalse(instance.getPaid());
    }

    @Test
    public void testBikeCorrectTrue() {
        boolean bike = true;
        ParkAndRide instance = new ParkAndRide(1, "correctName", "correctUrl", 100, true, bike, correctLocation);
        Assert.assertTrue(instance.getBike());
    }

    @Test
    public void testBikeCorrectFalse() {
        boolean bike = false;
        ParkAndRide instance = new ParkAndRide(1, "correctName", "correctUrl", 100, true, bike, correctLocation);
        Assert.assertFalse(instance.getBike());
    }

}
