package nl.klm.fares.model;

public class AggregatedFares {

    private double amount;
    private Currency currency;
    private Location originAirport;
    private Location destinationAirport;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Location getOriginAirport() {
        return originAirport;
    }

    public void setOriginAirport(Location originAirport) {
        this.originAirport = originAirport;
    }

    public Location getDestinationAirport() {
        return destinationAirport;
    }

    public void setDestinationAirport(Location destinationAirport) {
        this.destinationAirport = destinationAirport;
    }
}
