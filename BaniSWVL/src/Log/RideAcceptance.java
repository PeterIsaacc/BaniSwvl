package Log;

import Rides.RideRequest;

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
        return ("Event Type: User accepts price set by Captain\n"
                + "Event Time: " + this.eventTime + "\n"
                + "User's Name: " + this.clientUserName);
    }
}
