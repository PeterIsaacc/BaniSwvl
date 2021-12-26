package Users;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import System.*;

public class Admin extends User{
    public Admin(Info userData) {
        super(userData);
    }

    public void verfiyDriverRegistration(String driverUserName, ArrayList<Driver> pendingDrivers)
    {
        for(int i = 0; i < pendingDrivers.size(); i++)
        {
            Driver driver = pendingDrivers.get(i);
            if(driver.getUserData().getUserName().equals(driverUserName))
            {
                driver.setState(State.Accepted);
                pendingDrivers.remove(i);
                return;
            }
        }
    }

    public void suspendUser(User user)
    {
        if(user instanceof Client)
        {
            ((Client) user).setState(State.Suspended);
        }
        if(user instanceof Driver)
        {
            ((Driver) user).setState(State.Suspended);
        }
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
        int choice = Main.input.nextInt();
        input.nextLine();
        switch (choice) {
            case 1 -> {
                Main.system.listPendingDrivers();
            }
            case 2 -> {
                Main.system.listAllUsers();
            }
            case 3 -> {
                AdminAcceptingDriver();
            }
            case 4 -> {
                AdminSuspendingUser();
            }
            case 5 -> {
                return null;
            }

        }
        return this;
    }

    //displayMenu Functions
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
    //end of displayMenu functions

    public String toString()
    {
        String string = "--Admin--\n";
        string+=this.getUserData().toString();
        return string;
    }
}
