package domain;

public enum TicketType {
    DAY_TICKET, SEASON_TICKET;

    private String displayValue;

    static {
        DAY_TICKET.displayValue = "Dagticket";
        SEASON_TICKET.displayValue = "Abonnement";
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
