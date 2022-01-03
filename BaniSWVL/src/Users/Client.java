package Users;

import Rides.Offer;
import Rides.RideRequest;
import System.MainSystem;
import UI.Main;

import java.util.ArrayList;
import java.util.Scanner;


public class Client extends User {
    private State state;
    private final ArrayList<Offer> offers;
    private final ArrayList<RideRequest> rideRequests;
    private int numberofrides;

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
        state = State.Available;
        numberofrides = 0;
    }

    public ArrayList<Offer> getOffers() {
        return offers;
    }

    public ArrayList<RideRequest> getRideRequests() {
        return rideRequests;
    }

    public int getNumberofrides() {
        return numberofrides;
    }

    public void setNumberofrides(int numberofrides) {
        this.numberofrides = numberofrides;
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
        System.out.print("Enter Source: ");
        String source = Main.input.nextLine();
        System.out.print("Enter Destination: ");
        String destination = Main.input.nextLine();

        System.out.print("Number of Passengers: ");
        String numberOfPassengersString = Main.input.nextLine();
        int numberOfPassengers = Integer.parseInt(numberOfPassengersString);

        RideRequest rideRequest = requestaRide(source, destination, numberOfPassengers);

        boolean success = system.updateSystemRideRequests(rideRequest);

        if (success) System.out.println("Relevant drivers have been notified...");
        else System.out.println("Area doesn't exist in our database!");
    }

    public void rateDriver(MainSystem system) {
        System.out.println("----------Rate Driver----------");
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


    public RideRequest requestaRide(String source, String destination, int numberOfPassengers) {
        RideRequest rideRequest = new RideRequest(source, destination, getUserData().getUserName(), numberOfPassengers);
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
            System.out.println("\t" + "Offer " + i++ + "\n" + offer);
        }
        Scanner input = new Scanner(System.in);
        System.out.println("Which offer do you want to Accept? (enter an index starting from 1)");
        int choice = input.nextInt();
        if (system.clientAcceptOffer(offers.get(choice - 1), this)) {
            System.out.println("the driver has been notified");
            offers.remove(choice - 1);
        }
        else
            System.out.println("Driver is busy right now");
    }


    public String toString() {
        String string = "--Client--\n";
        string += this.getUserData().toString();
        string += ("\nState: " + state);
        return string;
    }
}


