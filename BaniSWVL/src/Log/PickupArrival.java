package Log;

import Rides.Offer;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PickupArrival implements Event {
    Date eventTime;
    private final String clientUserName;
    private String driverUserName;

    public PickupArrival(Date eventTime, Offer offer) {
        this.eventTime = eventTime;
        this.driverUserName = offer.getDriverUserName();
        this.clientUserName = offer.getRideRequest().getClientUserName();
    }

    @Override
    public String toString() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return ("Event Type: Captain arrived to user location\n"
                + "Event Time: " + formatter.format(this.eventTime) + "\n"
                + "Captain's Name: " + this.driverUserName
                + "\nUser's Name: " + this.clientUserName);
    }
}
