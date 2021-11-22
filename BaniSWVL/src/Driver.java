import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Driver extends User{
    private Set<String> areas;
    private boolean available;
    private double avgRating;
    private ArrayList<UserRating> userRatings;
    private ArrayList<RideRequest> rideRequests;
    private State driverState;

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public State getDriverState() {
        return driverState;
    }

    public void setDriverState(State driverState) {
        this.driverState = driverState;
    }

    public Driver(Info userData) {
        super(userData);areas = new HashSet<String>();
    }
    public void menu(){
        System.out.println("this is driver");
    }
    public void addArea(String area)
    {
        areas.add(area);
    }
    public void listUserRatings()
    {
        for(int i = 0; i < userRatings.size(); i++)
            System.out.println(userRatings.get(i));
    }
    public void listRides(){
        for(int i = 0; i < rideRequests.size(); i++)
        {
            System.out.println(rideRequests.get(i));
        }
    }
    public void addRating(UserRating rating)
    {
        userRatings.add(rating);
    }
    public void addRideRequest(RideRequest rideRequest)
    {
        rideRequests.add(rideRequest);
    }
    public Offer makeOffer(RideRequest rideRequest, double price)
    {
        return new Offer(rideRequest,price);
    }
    public boolean containArea(String area)
    {
        return areas.contains(area);
    }


}
