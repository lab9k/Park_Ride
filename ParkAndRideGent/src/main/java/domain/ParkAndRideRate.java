package domain;

import java.math.BigDecimal;
import java.time.Period;

public class ParkAndRideRate {

    //<editor-fold defaultstate="expanded" desc="Attributes">
    private BigDecimal price;
    private TicketType ticketType;
    private Period validFor;
    private String notes;
    //</editor-fold>

    //<editor-fold defaultstate="expanded" desc="Constructors">
    public ParkAndRideRate(BigDecimal price, TicketType ticketType, Period validFor) {
        setPrice(price);
        setTicketType(ticketType);
        setValidFor(validFor);
    }
    //</editor-fold>

    //<editor-fold defaultstate="expanded" desc="Getters">
    public BigDecimal getPrice() {
        return price;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public Period getValidFor() {
        return validFor;
    }

    public String getNotes() {
        return notes;
    }
    //</editor-fold>

    //<editor-fold defaultstate="expanded" desc="Setters">
    public void setPrice(BigDecimal price) {
        if(price == null)
            throw new IllegalArgumentException("ParkAndRideRate price can not be null");
        if(price.doubleValue() <= 0)
            throw new IllegalArgumentException("ParkAndRideRate price can not be negative or zero");

        this.price = price;
    }

    public void setTicketType(TicketType ticketType) {
        if(ticketType == null)
            throw new IllegalArgumentException("ParkAndRideRate ticketType can not be null");

        this.ticketType = ticketType;
    }

    public void setValidFor(Period validFor) {
        if(validFor == null)
            throw new IllegalArgumentException("ParkAndRideRate validFor can not be null");
        if(validFor.isNegative() || validFor.isZero())
            throw new IllegalArgumentException("ParkAndRideRate validFor can not be zero or negative");

        this.validFor = validFor;
    }

    public void setNotes(String notes) {
        if(notes == null || notes.equals(""))
            throw new IllegalArgumentException("ParkAndRideRate notes can not be empty");
        this.notes = notes;
    }
    //</editor-fold>

    //<editor-fold defaultstate="expanded" desc="Other Methods">
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(ticketType.getDisplayValue());

        if(ticketType == TicketType.SEASON_TICKET) {
            sb.append(" van ").append(validFor.getMonths()).append(" maanden");
        }

        return sb.toString();
    }
    //</editor-fold>

}
