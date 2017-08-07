package domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class SubrouteTest {

    private Subroute sr = Mockito.mock(Subroute.class, Mockito.CALLS_REAL_METHODS);

    @Test(expected = IllegalArgumentException.class)
    public void testStartTimeNotBeforeToday() {
        sr.setStartTime(LocalDateTime.now().minus(1, ChronoUnit.DAYS));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testStartTimeNotNull() {
        sr.setStartTime(null);
    }

    @Test
    public void testCorrectStartTime() {
        LocalDateTime ldt = LocalDateTime.now();
        sr.setStartTime(ldt);
        Assert.assertEquals(ldt, sr.getStartTime());
    }

    @Test
    public void testStartTimeEarlierToday() {
        LocalDateTime ldt = LocalDateTime.now().minus(10, ChronoUnit.SECONDS);
        sr.setStartTime(ldt);
        Assert.assertEquals(ldt, sr.getStartTime());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEndTimeNotBeforeToday() {
        sr.setEndTime(LocalDateTime.now().minus(1, ChronoUnit.DAYS));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEndTimeNotNull() {
        sr.setEndTime(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEndTimeNotBeforeBeginTime() {
        sr.setStartTime(LocalDateTime.now().plus(1, ChronoUnit.DAYS));
        sr.setEndTime(LocalDateTime.now());
    }

    @Test
    public void testCorrectEndTime() {
        LocalDateTime ldt = LocalDateTime.now();
        sr.setEndTime(ldt);
        Assert.assertEquals(ldt, sr.getEndTime());
    }

    @Test
    public void testEndTimeEarlierToday() {
        LocalDateTime ldt = LocalDateTime.now().minus(10, ChronoUnit.SECONDS);
        sr.setEndTime(ldt);
        Assert.assertEquals(ldt, sr.getEndTime());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testStartLocationNotNull() {
        sr.setStartLocation(null);
    }

    @Test
    public void testStartLocationCorrect() {
        Location l = new Location(0, 0);
        sr.setStartLocation(l);
        Assert.assertEquals(l, sr.getStartLocation());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEndLocationNotNull() {
        sr.setEndLocation(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEndLocationDifferentFromStartLocation() {
        sr.setStartLocation(new Location(0,0));
        sr.setEndLocation(new Location(0,0));
    }

    @Test
    public void testEndLocationCorrect() {
        Location l = new Location(0, 0);
        sr.setEndLocation(l);
        Assert.assertEquals(l, sr.getEndLocation());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLenghtNotNegative() {
        sr.setLength(-100);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLengthNotZero() {
        sr.setLength(0);
    }

    @Test
    public void testLengthCorrect() {
        double l = 10;
        sr.setLength(l);
        Assert.assertEquals(l, sr.getLength(), 0.00);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPriceNotNegative() {
        sr.setPrice(new BigDecimal(-100));
    }

    @Test
    public void testPriceCorrect() {
        BigDecimal p = new BigDecimal(100);
        sr.setPrice(p);
        Assert.assertEquals(p, sr.getPrice());
    }

    @Test
    public void testPriceZero() {
        BigDecimal p = new BigDecimal(0);
        sr.setPrice(p);
        Assert.assertEquals(p, sr.getPrice());
    }
    
}
