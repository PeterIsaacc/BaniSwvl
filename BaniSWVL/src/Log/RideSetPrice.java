package Log;

import Rides.Offer;

import java.text.SimpleDateFormat;
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
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return ("\nEvent Type: Captain set a price for ride\n"
                + "Event Time: " + formatter.format(this.eventTime) + "\n"
                + "Captain's Name: " + this.driverUserName
                + "\nRide Price: " + this.price);
    }
}
