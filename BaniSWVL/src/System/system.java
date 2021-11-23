package System;

import Rides.*;
import Users.*;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class system {
    Map<String, User> userDatabase;
    Map<String, ArrayList<String>> AreaToDriverMap;
    ArrayList<Driver> pendingDrivers;

    public system(Map<String, User> userDatabase) {
        this.userDatabase = userDatabase;
        AreaToDriverMap = new HashMap<String, ArrayList<String>>();
        pendingDrivers = new ArrayList<Driver>();
    }

    public User getUser(String userName) {
        if (userDatabase.containsKey(userName)) {
            return userDatabase.get(userName);
        }
        return null;
    }

    public system() {
        userDatabase = new HashMap<>();
        AreaToDriverMap = new HashMap<String, ArrayList<String>>();
        pendingDrivers = new ArrayList<Driver>();
    }

    public User login(String username, String password) {
        if (userDatabase.get(username).getUserData().getPassword().equals(password)) {
            User user = userDatabase.get(username);
            if (user instanceof Admin)
                return user;
            if (user instanceof Driver) {
                if (((Driver) user).getDriverState() == State.Suspended) {
                    System.out.println("This account is suspended");
                } else if (((Driver) user).getDriverState() == State.Pending) {
                    System.out.println("This account is pending");
                } else return user;
            } else if (user instanceof Client) {
                if (((Client) user).getState() == State.Suspended) {
                    System.out.println("This account is suspended");
                } else return user;
            }

        } else
            System.out.println("username or password is wrong");
        return null;
    }

    public User register(Info data) {
        if (userDatabase.containsKey(data.getUserName())) {
            System.out.println("username already exists");
            return null;
        }

        User newUser;
        if (data instanceof DriverInfo) {
            newUser = new Driver(data);
            pendingDrivers.add((Driver) newUser);
        } else newUser = new Client(data);

        return userDatabase.put(data.getUserName(), newUser);
    }

    public void addAdmin(User user) {
        if (user instanceof Admin) {
            userDatabase.put(user.getUserData().getUserName(), user);
        } else
            System.out.println("This is not an admin");
    }

    public boolean notifyDrivers(RideRequest rideRequest) {
        String area = rideRequest.getSource();
        if (AreaToDriverMap.containsKey(area)) {
            ArrayList<String> drivers = AreaToDriverMap.get(area);
            for (int i = 0; i < drivers.size(); i++) {
                String userName = drivers.get(i);
                Driver driver = (Driver) userDatabase.get(userName);
                driver.addRideRequest(rideRequest);
            }
            return true;
        }
        return false;
    }

    public boolean rateAdriver(UserRating userRating, String driverUserName) {
        if (userDatabase.containsKey(driverUserName)) {
            User user = userDatabase.get(driverUserName);
            if (user instanceof Driver) {
                Driver driver = (Driver) user;
                driver.addRating(userRating);
                return true;
            } else return false;
        } else return false;
    }

    public boolean addAreaToDriver(String area, Driver driver) {
        if (driver.containArea(area))
            return false;
        driver.addArea(area);
        String username = driver.getUserData().getUserName();
        if (!AreaToDriverMap.containsKey(area)) {
            ArrayList<String> drivers = new ArrayList<String>();
            drivers.add(username);
            AreaToDriverMap.put(area, drivers);
        } else {
            AreaToDriverMap.get(area).add(username);
        }
        return true;
    }

    public boolean driverMakingOffer(Driver driver, int index, double price) {
        ArrayList<RideRequest> rideRequests = driver.getRideRequests();
        if (index >= rideRequests.size()) return false;
        RideRequest rideRequest = rideRequests.get(index);
        Offer offer = driver.makeOffer(rideRequest, price);
        Client cl = (Client) userDatabase.get(rideRequest.getClientUserName());
        cl.addOffer(offer);
        rideRequests.remove(index);
        return true;
    }

    public void listAllUsers() {
        for (Map.Entry<String, User> e : userDatabase.entrySet()) {
            System.out.println(e.getValue());
            System.out.println();
        }
        System.out.println();
    }

    public void listPendingDrivers() {
        if (pendingDrivers.size() == 0) {
            System.out.println("no pending drivers exist");
            return;
        }
        for (int i = 0; i < pendingDrivers.size(); i++)
            System.out.println(pendingDrivers.get(i));
        System.out.println();
    }

    public void addUser(User user) {
        userDatabase.put(user.getUserData().getUserName(), user);
    }

    public ArrayList<Driver> getPendingDrivers() {
        return pendingDrivers;
    }


}
