package Log;

import Rides.Offer;

import java.util.Date;

public class RideSetPrice implements Event {
    Date eventTime;
    private double price;
    private String driverUserName;

    public RideSetPrice(Date eventTime, Offer offer) {
        this.eventTime = eventTime;
        this.price = offer.getPrice();
        this.driverUserName = offer.getDriverUserName();
    }

    @Override
    public String toString() {

        return ("\nEvent Type: Captain set a price for ride\n"
                + "Event Time: " + this.eventTime + "\n"
                + "Captain's Name: " + this.driverUserName
                + "Ride Price: " + this.price);
    }
}
