package Users;

import Rides.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Driver extends User {
    private Set<String> areas;
    private boolean available;
    private double avgRating;
    private ArrayList<UserRating> userRatings;
    private ArrayList<RideRequest> rideRequests;
    private ArrayList<Offer> offers;
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
        super(userData);
        areas = new HashSet<String>();
        rideRequests = new ArrayList<RideRequest>();
        userRatings = new ArrayList<UserRating>();
        offers = new ArrayList<Offer>();
        driverState = State.Pending;
        avgRating = 0;
    }

    public void addArea(String area) {
        areas.add(area);
    }

    public void listUserRatings() {
        if (userRatings.size() == 0) {
            System.out.println("no user ratings exist yet");
            System.out.println();
            return;
        }
        for (int i = 0; i < userRatings.size(); i++) {
            System.out.println(userRatings.get(i));
            System.out.println();
        }
    }

    public boolean listRides() {
        if (rideRequests.size() == 0) {
            System.out.println("no rides exist yet");
            System.out.println();
            return true;
        }
        for (int i = 0; i < rideRequests.size(); i++) {
            System.out.println(rideRequests.get(i));
            System.out.println();
        }
        return false;
    }

    public void addRating(UserRating rating) {
        for (int i = 0; i < userRatings.size(); i++) {
            if (rating.getUserName() == userRatings.get(i).getUserName()) {
                userRatings.set(i, rating);
                calculateAvgRating();
                return;
            }
        }
        userRatings.add(rating);
        calculateAvgRating();
    }

    public void addRideRequest(RideRequest rideRequest) {
        rideRequests.add(rideRequest);
    }

    public Offer makeOffer(RideRequest rideRequest, double price) {
        Offer offer = new Offer(rideRequest, price, getUserData().getUserName(), avgRating);
        offers.add(offer);
        return offer;
    }

    public void listOffers() {
        if (offers.size() == 0) {
            System.out.println("no offers exist yet");
            System.out.println();
            return;
        }
        for (int i = 0; i < offers.size(); i++) {
            System.out.println(offers.get(i));
            System.out.println();
        }
    }

    public boolean containArea(String area) {
        return areas.contains(area);
    }

    public ArrayList<RideRequest> getRideRequests() {
        return rideRequests;
    }

    public void addOffer(Offer offer) {
        offers.add(offer);
    }

    private void calculateAvgRating() {
        int sum = 0;
        for (int i = 0; i < userRatings.size(); i++) {
            sum += userRatings.get(i).getRating();
        }
        avgRating = (double) sum / userRatings.size();
    }

    public void setState(State state) {
        this.driverState = state;
    }

    public String toString() {
        String string = "--Driver--\n";
        string += getUserData().toString();
        string += ("\ndriver state: " + driverState);
        return string;
    }

}
