package com.example.Log;

import com.example.Rides.Offer;

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
        return (",Event Type: Captain set a price for ride, "
                + "Event Time: " + formatter.format(this.eventTime) + ", "
                + "Captain's Name: " + this.driverUserName
                + ", Ride Price: " + this.price);
    }
}
