package domain;

import org.junit.Assert;
import org.junit.Test;

public class LocationTest {

    @Test(expected = IllegalArgumentException.class)
    public void testLatitudeTooSmall() {
        double latitude = -100;
        Location l = new Location(latitude, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLatitudeTooBig() {
        double latitude = 100;
        Location l = new Location(latitude, 0);
    }

    @Test
    public void testcorrectLatitude() {
        double latitude = 85;
        Location l = new Location(latitude, 0);
        Assert.assertEquals(latitude, l.getLatitude(), 0.0000);
    }

    @Test
    public void testLatitudeMax() {
        double latitude = 90;
        Location l = new Location(latitude, 0);
        Assert.assertEquals(latitude, l.getLatitude(), 0.0000);
    }

    @Test
    public void testLatitudeMin() {
        double latitude = -90;
        Location l = new Location(latitude, 0);
        Assert.assertEquals(latitude, l.getLatitude(), 0.0000);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLongitudTooSmall() {
        double longitude = -190;
        Location l = new Location(0, longitude);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLongitudeTooBig() {
        double longitude = 190;
        Location l = new Location(0, longitude);
    }

    @Test
    public void testcorrectLongitude() {
        double longitude = 85;
        Location l = new Location(0, longitude);
        Assert.assertEquals(longitude, l.getLongitude(), 0.0000);
    }

    @Test
    public void testLongitudeMax() {
        double longitude = 180;
        Location l = new Location(0, longitude);
        Assert.assertEquals(longitude, l.getLongitude(), 0.0000);
    }

    @Test
    public void testLongitudeMin() {
        double longitude = -180;
        Location l = new Location(0, longitude);
        Assert.assertEquals(longitude, l.getLongitude(), 0.0000);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNameNotNull() {
        Location l = new Location(0, 0, null);
    }

    @Test
    public void testFormattedCoordinates() {
        double latitude = 90;
        double longitude = 100;
        Location l = new Location(latitude, longitude);
        Assert.assertEquals(latitude + "," + longitude, l.getFormattedCoordinates());
    }

}
