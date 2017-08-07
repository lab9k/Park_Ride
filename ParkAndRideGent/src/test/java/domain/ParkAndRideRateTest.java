package domain;

import java.math.BigDecimal;
import java.time.Period;
import org.junit.Assert;
import org.junit.Test;

public class ParkAndRideRateTest {

    @Test(expected = IllegalArgumentException.class)
    public void testPriceZero() {
        ParkAndRideRate prr = new ParkAndRideRate(BigDecimal.ZERO, TicketType.DAY_TICKET, Period.of(1, 0, 0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPriceNull() {
        ParkAndRideRate prr = new ParkAndRideRate(null, TicketType.DAY_TICKET, Period.of(1, 0, 0));
    }

    @Test
    public void testPriceCorrect() {
        ParkAndRideRate prr = new ParkAndRideRate(BigDecimal.ONE, TicketType.DAY_TICKET, Period.of(1, 0, 0));
        Assert.assertEquals(BigDecimal.ONE, prr.getPrice());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTicketTypeNull() {
        ParkAndRideRate prr = new ParkAndRideRate(BigDecimal.ONE, null, Period.of(1, 0, 0));
    }

    @Test
    public void testTicketTypeCorrect() {
        ParkAndRideRate prr = new ParkAndRideRate(BigDecimal.ONE, TicketType.DAY_TICKET, Period.of(1, 0, 0));
        Assert.assertEquals(TicketType.DAY_TICKET, prr.getTicketType());

    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidForNull() {
        ParkAndRideRate prr = new ParkAndRideRate(BigDecimal.ONE, TicketType.DAY_TICKET, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidForNegative() {
        ParkAndRideRate prr = new ParkAndRideRate(BigDecimal.ONE, TicketType.DAY_TICKET, Period.of(-1, 0, 0));
    }

    @Test
    public void testValidForCorrect() {
        ParkAndRideRate prr = new ParkAndRideRate(BigDecimal.ONE, TicketType.DAY_TICKET, Period.of(1, 0, 0));
        Assert.assertEquals(Period.of(1,0,0), prr.getValidFor());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNotesNull() {
        ParkAndRideRate prr = new ParkAndRideRate(BigDecimal.ONE, TicketType.DAY_TICKET, Period.of(1, 0, 0));
        prr.setNotes(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNotesEmpty() {
        ParkAndRideRate prr = new ParkAndRideRate(BigDecimal.ONE, TicketType.DAY_TICKET, Period.of(1, 0, 0));
        prr.setNotes("");
    }

    @Test
    public void testNotesCorrect() {
        ParkAndRideRate prr = new ParkAndRideRate(BigDecimal.ONE, TicketType.DAY_TICKET, Period.of(1, 0, 0));
        prr.setNotes("test notes");
        Assert.assertEquals("test notes", prr.getNotes());
    }

}
