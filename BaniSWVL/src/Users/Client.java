package Users;

import Rides.*;
import System.*;
import UI.Main;

import java.util.ArrayList;
import java.util.Scanner;


public class Client extends User {
    private State state;
    private final ArrayList<Offer> offers;
    private final ArrayList<RideRequest> rideRequests;

    public State getState() {
        return state;
    }

    public boolean setState(State state) {
        this.state = state;
        return true;
    }

    public Client(Info userData) {
        super(userData);
        rideRequests = new ArrayList<>();
        offers = new ArrayList<>();
        state = State.Avilable;
    }

    public User displayMenu(MainSystem system) {
        System.out.println("----------" + "Client Menu" + "----------");
        System.out.println("""
                1. Request a ride
                2. Rate a driver
                3. List offers
                4. Logout
                """);
        return options(system);
    }

    public User options(MainSystem system) {
        int choice = Main.input.nextInt();
        Main.input.nextLine();
        switch (choice) {
            case 1 -> requestRide(system);
            case 2 -> rateDriver(system);
            case 3 -> this.listAndChooseOffer(system);
            case 4 -> {
                return null;
            }
            default -> throw new IllegalStateException("Unexpected value: " + choice);
        }
        return this;
    }

    //options functions
    public void requestRide(MainSystem system) {
        System.out.println("----------" + "Request Ride" + "----------");
        System.out.println("Enter Source: ");
        String source = Main.input.nextLine();
        System.out.println("Enter Destination: ");
        String destination = Main.input.nextLine();

        RideRequest rideRequest = this.rideRequest(source, destination);
        boolean success = system.notifyDrivers(rideRequest);
        if (success) System.out.println("Relevant drivers have been notified...");
        else System.out.println("Area doesn't exist in our database!");
    }

    public void rateDriver(MainSystem system) {
        System.out.println("----------" + "Rate Driver" + "----------");
        System.out.println("Enter driver's username: ");
        String userName = Main.input.nextLine();
        System.out.println("Enter rating from 1 to 5");
        int rating = Main.input.nextInt();
        Main.input.nextLine();
        System.out.println("Any Comments? ");
        String comment = Main.input.nextLine();

        UserRating userRating = this.rateDriver(rating, comment);
        boolean success = system.rateDriver(userRating, userName);
        if (success) System.out.println("Rating submitted for" + userName);
        else System.out.println("no such driver exists");
    }
    //end of options functions


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
        for (RideRequest rideRequest : rideRequests) {
            System.out.println(rideRequest);
        }
    }

    public void listAndChooseOffer(MainSystem system) {
        if (offers.size() == 0) {
            System.out.println("no offers yet...");
            return;
        }
        System.out.println("------Choose An Offer------");
        int i = 1;
        for (Offer offer : offers) {
            System.out.println(i++ + ": " + offer);
        }
        Scanner input = new Scanner(System.in);
        int choice = input.nextInt();
        System.out.println("Which offer do you want to Accept? (enter an index starting from 1");
        system.clientAcceptOffer(offers.get(choice - 1));
        offers.remove(choice - 1);
    }

    public String toString() {
        String string = "--Client--\n";
        string += this.getUserData().toString();
        string += ("\nState: " + state);
        return string;
    }
}


