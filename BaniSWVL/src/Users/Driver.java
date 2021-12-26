package Users;

import Rides.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import System.*;
import UI.Main;

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

    public User displayMenu(MainSystem system) {

        System.out.println("----------" + "Driver Menu" + "----------");
        System.out.println("""
                1. Add an area
                2. List user ratings
                3. List rides or make an offer
                4. List offer you made
                5. Logout
                """);
        return options(system);
    }

    public User options(MainSystem system) {
        int choice = Main.input.nextInt();
        Main.input.nextLine();
        switch (choice) {
            case 1 -> {
                driverAddingArea(system);
            }
            case 2 -> {
                this.listUserRatings();
            }
            case 3 -> {
                driverListingRides(system);
            }
            case 4 -> {
                this.listOffers();
            }
            case 5 -> {
                return null;
            }
            default -> throw new IllegalStateException("Unexpected value: " + choice);
        }
        return this;
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

    public boolean setState(State state) {
        this.driverState = state;
        return true;
    }

    public String toString() {
        String string = "--Driver--\n";
        string += getUserData().toString();
        string += ("\ndriver state: " + driverState);
        return string;
    }

    //displayMenu functions
    public void driverAddingArea(MainSystem system) {
        System.out.println("----------" + "Add Area" + "----------");
        System.out.println("Enter area name: ");
        String area = Main.input.nextLine();
        area = area.toLowerCase();
        boolean success = system.addAreaToDriver(area, this);
        if (success) System.out.println("Area added successfully...");
        else System.out.println("Area already exists...");
    }

    public void driverListingRides(MainSystem system) {
        System.out.println("----------" + "Available Ride Requests" + "----------");
        boolean empty = this.listRides();
        if (empty) return;
        System.out.print("Would you like to make an offer? (y/n)");
        String choice = Main.input.nextLine();
        if (choice.equals("y")) {
            System.out.println("Which ride do you want to make an offer to? (enter an index starting from 1)");
            int index = Main.input.nextInt();
            index--;
            System.out.println("Enter price: ");
            double price = Main.input.nextDouble();
            Main.input.nextLine();
            boolean success = system.driverMakingOffer(this, index, price);
            if (success) System.out.println("Offer made successfully...");
            else System.out.println("Invalid index");
        }
    }
    //end of displayMenu functions

}
