package domain;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class PublicTransportSubrouteTest {

    private PublicTransportSubroute sr =
            Mockito.mock(PublicTransportSubroute.class, Mockito.CALLS_REAL_METHODS);

    @Test(expected = IllegalArgumentException.class)
    public void testNameNotNull() {
        sr.setName(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNameEmpty() {
        sr.setName("");
    }

    @Test
    public void testNameCorrect() {
        String name = "correctname";
        sr.setName(name);
        Assert.assertEquals(name, sr.getName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNumberNotEmpty() {
        sr.setNumber(null);
    }

    @Test
    public void testNumberCorrect() {
        String number = "42";
        sr.setNumber(number);
        Assert.assertEquals(number, sr.getNumber());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testColorNotEmpty() {
        sr.setColor(null);
    }

    @Test
    public void testColorCorrect() {
        String color = "#229922";
        sr.setColor(color);
        Assert.assertEquals(color, sr.getColor());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFrequencyNotNegative() {
        sr.setFrequency(-0.5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFrequencyNotZero() {
        sr.setFrequency(0);
    }

    @Test
    public void testFrequencyCorrect() {
        double fr = 0.5;
        sr.setFrequency(fr);
        Assert.assertEquals(fr, sr.getFrequency(), 0.00);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLastReturnNotNull() {
        sr.setLastReturn(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLastReturnNotBeforeToday() {
        sr.setLastReturn(LocalDateTime.now().minus(1, ChronoUnit.DAYS));
    }

    @Test
    public void testLastReturnCorrect() {
        LocalDateTime ldt = LocalDateTime.now().plus(2,ChronoUnit.HOURS);
        sr.setLastReturn(ldt);
        Assert.assertEquals(ldt, sr.getLastReturn());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testStopsNotNull() {
        sr.setStops(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testStopsNotEmpty() {
        sr.setStops(new ArrayList<>());
    }

    @Test
    public void testStopsCorrect() {
        List<String> stops = new ArrayList<>();
        stops.add("test");
        sr.setStops(stops);
        Assert.assertEquals(stops, sr.getStops());

    }

}
