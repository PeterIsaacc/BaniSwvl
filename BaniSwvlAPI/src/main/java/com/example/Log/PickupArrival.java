package com.example.Log;

import com.example.Rides.Offer;

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
        return (", Event Type: Captain arrived to user location, "
                + "Event Time: " + formatter.format(this.eventTime) + ", "
                + "Captain's Name: " + this.driverUserName
                + ", User's Name: " + this.clientUserName);
    }
}
