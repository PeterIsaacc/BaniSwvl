package UI;

import System.*;
import Users.*;
import Rides.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    static User admin1 = new Admin(new Info("shehab", "010000000", "0000000000", "00"));
    static User admin2 = new Admin(new Info("peter", "010000000", "0000000000", "00"));
    static User admin3 = new Admin(new Info("david", "010000000", "0000000000", "00"));
    static User admin4 = new Admin(new Info("abdallah", "010000000", "0000000000", "00"));

    Map<String, User> database = new HashMap<String, User>();
    static MainSystem system = new MemorySystem();
    static User currentUser = null;
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        system.addAdmin(admin1);
        system.addAdmin(admin2);
        system.addAdmin(admin3);
        system.addAdmin(admin4);

        while (true) {
            while (currentUser == null) {
                System.out.println("--------------------" + "BaniSWVL" + "--------------------");
                System.out.println("""
                        1. Login
                        2. Register
                        """);
                switch (input.nextLine()) {
                    case "1" -> {
                        System.out.println("--------------------" + "Login" + "--------------------");
                        System.out.print("Username: ");
                        String username = input.nextLine();
                        System.out.print("Password: ");
                        currentUser = system.login(username, input.nextLine());
                    }
                    case "2" -> {
                        System.out.println("----------" + "Choose Account Type" + "----------");
                        System.out.println("""
                                1. Register as Driver
                                2. Register as Client
                                """);
                        System.out.println("----------" + "Enter Info" + "----------");
                        String choice = input.nextLine();
                        System.out.print("Username: ");
                        String userName = input.nextLine();
                        System.out.print("Mobile Number: ");
                        String mobileNumber = input.nextLine();
                        System.out.print("Email: ");
                        String email = input.nextLine();
                        System.out.print("Password: ");
                        String password = input.nextLine();
                        Info data = new Info(userName, mobileNumber, email, password);
                        switch (choice) {
                            case "1" -> {
                                System.out.println("----------" + "Extra Driver Info" + "----------");
                                System.out.print("Driver's License: ");
                                String driverLicense = input.nextLine();
                                System.out.print("National ID: ");
                                String nationalId = input.nextLine();
                                currentUser = system.register(new DriverInfo(data, driverLicense, nationalId));
                            }
                            case "2" -> {
                                currentUser = system.register(data);
                            }
                            default -> throw new IllegalStateException("Unexpected value: " + choice);
                        }
                    }
                    default -> throw new IllegalStateException("Unexpected value: " + input.nextInt());
                }
            }
            while (currentUser != null) {
                if (currentUser instanceof Client) {
                    displayClientMenu();
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
                            ((Client) currentUser).listOffers();
                        }
                        case 4 -> {
                            currentUser = null;
                        }
                        default -> throw new IllegalStateException("Unexpected value: " + choice);
                    }

                } else if (currentUser instanceof Driver) {
                    displayDriverMenu();
                    int choice = input.nextInt();
                    input.nextLine();
                    switch (choice) {
                        case 1 -> {
                            driverAddingArea();
                        }
                        case 2 -> {
                            ((Driver) currentUser).listUserRatings();
                        }
                        case 3 -> {
                            driverListingRides();
                        }
                        case 4 -> {
                            ((Driver) currentUser).listOffers();
                        }
                        case 5 -> {
                            currentUser = null;
                        }
                        default -> throw new IllegalStateException("Unexpected value: " + choice);
                    }

                } else if (currentUser instanceof Admin) {
                    displayAdminMenu();
                    int choice = input.nextInt();
                    input.nextLine();
                    switch (choice) {
                        case 1 -> {
                            system.listPendingDrivers();
                        }
                        case 2 -> {
                            system.listAllUsers();
                        }
                        case 3 -> {
                            AdminAcceptingDriver();
                        }
                        case 4 -> {
                            AdminSuspendingUser();
                        }
                        case 5 -> {
                            currentUser = null;
                        }

                    }
                }
            }
        }
    }

    public static void displayClientMenu() {
        System.out.println("----------" + "Client Menu" + "----------");
        System.out.println("""
                1. Request a ride
                2. Rate a driver
                3. List offers
                4. Logout
                """);
    }

    public static void clientRequestingRide() {
        System.out.println("----------" + "Request Ride" + "----------");
        System.out.println("Enter Source: ");
        String source = input.nextLine();
        System.out.println("Enter Destination: ");
        String destination = input.nextLine();
        Client cl = (Client) currentUser;
        RideRequest rideRequest = cl.rideRequest(source, destination);
        boolean success = system.notifyDrivers(rideRequest);
        if (success) System.out.println("Relevant drivers have been notified...");
        else System.out.println("Area doesn't exist in our database!");
    }

    public static void clientRatingDriver() {
        System.out.println("----------" + "Rate Driver" + "----------");
        System.out.println("Enter driver's username: ");
        String userName = input.nextLine();
        System.out.println("Enter rating from 1 to 5");
        int rating = input.nextInt();
        input.nextLine();
        System.out.println("Any Comments? ");
        String comment = input.nextLine();

        Client cl = (Client) currentUser;
        UserRating userRating = cl.rateDriver(rating, comment);
        boolean success = system.rateAdriver(userRating, userName);
        if (success) System.out.println("Rating submitted for" + userName);
        else System.out.println("no such driver exists");
    }

    public static void displayDriverMenu() {
        System.out.println("----------" + "Driver Menu" + "----------");
        System.out.println("""
                1. Add an area
                2. List user ratings
                3. List rides or make an offer
                4. List offer you made
                5. Logout
                """);
    }

    public static void driverAddingArea() {
        System.out.println("----------" + "Add Area" + "----------");
        System.out.println("Enter area name: ");
        String area = input.nextLine();
        area = area.toLowerCase();
        boolean success = system.addAreaToDriver(area, (Driver) currentUser);
        if (success) System.out.println("Area added successfully...");
        else System.out.println("Area already exists...");
    }

    public static void driverListingRides() {
        System.out.println("----------" + "Available Ride Requests" + "----------");
        Driver driver = (Driver) currentUser;
        boolean empty = driver.listRides();
        if (empty) return;
        System.out.print("Would you like to make an offer? (y/n)");
        String choice = input.nextLine();
        if (choice.equals("y")) {
            System.out.println("Which ride do you want to make an offer to? (enter an index starting from 1)");
            int index = input.nextInt();
            index--;
            System.out.println("Enter price: ");
            double price = input.nextDouble();
            input.nextLine();
            boolean success = system.driverMakingOffer((Driver) currentUser, index, price);
            if (success) System.out.println("Offer made successfully...");
            else System.out.println("Invalid index");
        }
    }

    public static void displayAdminMenu() {
        System.out.println("----------" + "Admin Menu" + "----------");
        System.out.println("""
                1. List all pending drivers
                2. List all users
                3. Accept a driver request
                4. Suspend a user
                5. Logout
                """);

    }

    public static void AdminAcceptingDriver() {
        System.out.println("----------" + "Accept A Driver" + "----------");
        Admin admin = (Admin) currentUser;
        system.listPendingDrivers();
        System.out.println("---------------------------------------------");
        System.out.println("Enter driver's username you want to accept: ");
        String userName = input.nextLine();
        admin.verfiyDriverRegistration(userName, system.getPendingDrivers());
        System.out.println("Driver with username: " + userName + " accepted");
    }

    public static void AdminSuspendingUser() {
        System.out.println("----------" + "Suspend A User" + "----------");
        system.listAllUsers();
        System.out.println("Enter username for user you want to suspend: ");
        String userName = input.nextLine();
        User user = system.getUser(userName);
        if (user instanceof Admin) {
            System.out.println("You can't suspend an admin!");
            System.out.println();
            return;
        }
        Admin admin = (Admin) currentUser;
        admin.suspendUser(user);
        System.out.println("User with username: " + userName + " has been suspended");
    }
}

