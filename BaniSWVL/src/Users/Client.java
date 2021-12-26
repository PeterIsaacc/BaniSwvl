package Users;

import Rides.*;
import System.*;
import java.util.ArrayList;
import java.util.Scanner;


public class Client extends User {
    private State state;
    private ArrayList<Offer> offers;
    private ArrayList<RideRequest> rideRequests;

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Client(Info userData) {
        super(userData);
        rideRequests = new ArrayList<RideRequest>();
        offers = new ArrayList<Offer>();
        state = State.Accepted;
    }

    public User displayMenu(MainSystem system) {
        System.out.println("----------" + "Client Menu" + "----------");
        System.out.println("""
                1. Request a ride
                2. Rate a driver
                3. List offers
                4. Logout
                """);
        int choice = input.nextInt();
        input.nextLine();
        switch (choice) {
            case 1 -> {
                clientRequestingRide();
            }
            case 2 -> {
                clientRatingDriver();
            }
            case 3 -> {
                ((Client) this).listOffers();
            }
            case 4 ->{
                return null;
            }
            default -> throw new IllegalStateException("Unexpected value: " + choice);
        }
        return this;
    }
    //displayMenu functions
    public void clientRequestingRide() {
        System.out.println("----------" + "Request Ride" + "----------");
        System.out.println("Enter Source: ");
        String source = input.nextLine();
        System.out.println("Enter Destination: ");
        String destination = input.nextLine();
        Client cl = (Client) this;
        RideRequest rideRequest = cl.rideRequest(source, destination);
        boolean success = system.notifyDrivers(rideRequest);
        if (success) System.out.println("Relevant drivers have been notified...");
        else System.out.println("Area doesn't exist in our database!");
    }

    public void clientRatingDriver() {
        System.out.println("----------" + "Rate Driver" + "----------");
        System.out.println("Enter driver's username: ");
        String userName = input.nextLine();
        System.out.println("Enter rating from 1 to 5");
        int rating = input.nextInt();
        input.nextLine();
        System.out.println("Any Comments? ");
        String comment = input.nextLine();

        Client cl = (Client) this;
        UserRating userRating = cl.rateDriver(rating, comment);
        boolean success = system.rateAdriver(userRating, userName);
        if (success) System.out.println("Rating submitted for" + userName);
        else System.out.println("no such driver exists");
    }
    //end of displayMenu functions

    public RideRequest rideRequest(String source, String destination) {
        RideRequest rideRequest = new RideRequest(source, destination, getUserData().getUserName());
        rideRequests.add(rideRequest);
        return rideRequest;
    }

    public UserRating rateDriver(int rating, String comment) {
        return new UserRating(getUserData().getUserName(), rating, comment);
    }

    public void addOffer(Offer offer) {
        offers.add(offer);
    }

    public void addRideRequest(RideRequest rideRequest) {
        rideRequests.add(rideRequest);
    }

    public void listRideRequests() {
        if (rideRequests.size() == 0) {
            System.out.println("no rides exist yet");
            return;
        }
        for (int i = 0; i < rideRequests.size(); i++) {
            System.out.println(rideRequests.get(i));
        }
    }

    public void listOffers() {
        if (offers.size() == 0) {
            System.out.println("no offers exist yet");
            return;
        }
        for (int i = 0; i < offers.size(); i++) {
            System.out.println(offers.get(i));
        }
    }

    public String toString() {
        String string = "--Client--\n";
        string += this.getUserData().toString();
        string += ("\nState: " + state);
        return string;
    }
}


