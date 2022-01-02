package com.example.Log;

import com.example.Rides.RideRequest;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RideAcceptance implements Event {
    Date eventTime;
    private final String clientUserName;

    public RideAcceptance(Date eventTime, RideRequest request) {
        this.eventTime = eventTime;
        this.clientUserName = request.getClientUserName();
    }

    @Override
    public String toString() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return (",Event Type: User accepts price set by Captain, "
                + "Event Time: " + formatter.format(this.eventTime) + ", "
                + "User's Name: " + this.clientUserName);
    }
}
