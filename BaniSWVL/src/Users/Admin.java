package Users;

import UI.Main;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

import System.*;

public class Admin extends User {
    public Admin(Info userData) {
        super(userData);
    }

    public void verfiyDriverRegistration(String driverUserName, ArrayList<Driver> pendingDrivers) {
        for (int i = 0; i < pendingDrivers.size(); i++) {
            Driver driver = pendingDrivers.get(i);
            if (driver.getUserData().getUserName().equals(driverUserName)) {
                driver.setState(State.Accepted);
                pendingDrivers.remove(i);
                return;
            }
        }
    }

    public void suspendUser(User user) {
        if (user.setState(State.Suspended)) {
            System.out.println("User with username: " + user.getUserData().getUserName() + " has been suspended");
        } else
            System.out.println("Suspension failed");
    }

    public User displayMenu(MainSystem system) {
        System.out.println("----------" + "Admin Menu" + "----------");
        System.out.println("""
                1. List all pending drivers
                2. List all users
                3. Accept a driver request
                4. Suspend a user
                5. Logout
                """);
        return options(system);
    }

    public User options(MainSystem system) {
        int choice = Main.input.nextInt();
        Main.input.nextLine();
        switch (choice) {
            case 1 -> {
                system.listPendingDrivers();
            }
            case 2 -> {
                system.listAllUsers();
            }
            case 3 -> {
                AdminAcceptingDriver(system);
            }
            case 4 -> {
                AdminSuspendingUser(system);
            }
            case 5 -> {
                return null;
            }

        }
        return this;
    }

    //displayMenu Functions
    public void AdminAcceptingDriver(MainSystem system) {
        System.out.println("----------" + "Accept A Driver" + "----------");
        if (system.listPendingDrivers()) {
            System.out.println("---------------------------------------------");
            System.out.println("Enter driver's username you want to accept: ");
            String userName = Main.input.nextLine();
            this.verfiyDriverRegistration(userName, system.getPendingDrivers());
            System.out.println("Driver with username: " + userName + " accepted");
        }
    }

    public boolean setState(State state) {
        if (state == State.Suspended) {
            System.out.println("You can't change state of an Admin!");
            System.out.println();
        }
        return false;
    }

    public void AdminSuspendingUser(MainSystem system) {
        System.out.println("----------" + "Suspend A User" + "----------");
        system.listAllUsers();
        System.out.println("Enter username for user you want to suspend: ");
        String userName = Main.input.nextLine();
        User user = system.getUser(userName);
        this.suspendUser(user);
    }
    //end of displayMenu functions

    public String toString() {
        String string = "--Admin--\n";
        string += this.getUserData().toString();
        return string;
    }
}
