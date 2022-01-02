package com.example.System;

import com.example.Discounts.BirthdayDiscount;
import com.example.Discounts.FirstRideDiscount;
import com.example.Discounts.PublicHolidayDiscount;
import com.example.Discounts.TwoPassengerDiscount;
import com.example.Log.*;

import com.example.Log.MemoryLog;
import com.example.Log.RideAcceptance;
import com.example.Log.RideSetPrice;
import com.example.Rides.Offer;
import com.example.Rides.RideRequest;
import com.example.Users.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MemorySystem implements MainSystem {
    Map<String, User> userDatabase;
    Map<String, ArrayList<String>> AreaToDriverMap;
    ArrayList<Driver> pendingDrivers;
    Log logs;
    ArrayList<RideRequest> rideRequests;
    ArrayList<String> AreaDiscounts;
    public static ArrayList<Date> Holidays;


    public boolean checkAreaDiscounts(String area) {
        if (AreaDiscounts != null)
            return AreaDiscounts.contains(area);
        return false;
    }

    public boolean addAreaDiscounts(String area) {
        if (AreaDiscounts == null)
            AreaDiscounts = new ArrayList<>();
        if (AreaDiscounts.contains(area))
            return false;
        AreaDiscounts.add(area);
        return true;
    }

    public boolean checkdriver(Driver d, String Area) {
        if (AreaToDriverMap.containsKey(Area))
            return AreaToDriverMap.get(Area).contains(d.getUserData().getUserName());
        return false;
    }
    public boolean checkPendingDrivers(String userName) {
        for (Driver pendingDriver : pendingDrivers) {
            if (pendingDriver.getUserData().getUserName().equals(userName)) {return true;}
        }
        return false;
    }
    public ArrayList<RideRequest> getRideRequests() {
        return rideRequests;
    }

    public void setRideRequests(ArrayList<RideRequest> rideRequests) {
        this.rideRequests = rideRequests;
    }

    public Log getLogs() {
        return logs;
    }
    public void setLogs(Log logs) {
        this.logs = logs;
    }

    public User getUser(String userName) {
        if (userDatabase.containsKey(userName)) {
            return userDatabase.get(userName);
        }
        return null;
    }

    public MemorySystem() {
        this.userDatabase = new HashMap<>();
        AreaToDriverMap = new HashMap<>();
        this.pendingDrivers = new ArrayList<>();
        logs = new MemoryLog();
        this.rideRequests = new ArrayList<>();
        Holidays = new ArrayList<>();
        Holidays.add(new Date());
    }

    public User login(String username, String password) {
        User user = userDatabase.get(username);
        if(user == null)
            return null;
        if (user.getUserData().getPassword().equals(password)) {
                return user;
        }
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

    public boolean updateSystemRideRequests(RideRequest rideRequest) {
        String area = rideRequest.getSource();
        rideRequests.add(rideRequest);
        System.out.println("database updated");
        if (AreaToDriverMap.containsKey(area)) {
            return true;
        }
        return false;
    }

    public boolean rateDriver(UserRating userRating, String driverUserName) {
        if (userDatabase.containsKey(driverUserName)) {
            User user = userDatabase.get(driverUserName);
            if (user instanceof Driver driver) {
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
            ArrayList<String> drivers = new ArrayList<>();
            drivers.add(username);
            AreaToDriverMap.put(area, drivers);
        } else {
            AreaToDriverMap.get(area).add(username);
        }
        return true;
    }

    public boolean driverMakingOffer(Driver driver, RideRequest rideRequest, double price) {
        Offer offer = driver.makeOffer(rideRequest, price);
        Client cl = (Client) userDatabase.get(rideRequest.getClientUserName());
        cl.addOffer(offer);

        Date date = new Date();
        logs.addEvent(new RideSetPrice(date, offer));

        return true;
    }
    public ArrayList<Driver> ListPendingDrivers() {
        return pendingDrivers;
    }
    public boolean clientAcceptOffer(Offer offer, Client client) {

        rideRequests.remove(offer.getRideRequest());
        Driver driver = (Driver) userDatabase.get(offer.getDriverUserName());

        Date date = new Date();
        logs.addEvent(new RideAcceptance(date, offer.getRideRequest()));

        //number of passengers discount
        if (offer.getOffer().getRideRequest().getNumberOfPassengers() >= 2) {
            offer = new TwoPassengerDiscount(offer);
        }

        // first ride discount
        if (client.getNumberofrides() == 0)
            offer = new FirstRideDiscount(offer);

        // Birthday discount
        Date now = new Date();
        if (client.getUserData().getDob().getDay() == now.getDay() && client.getUserData().getDob().getMonth() == now.getMonth()) {
            offer = new BirthdayDiscount(offer);
        }

        //public holiday discount
        for (Date holi : Holidays) {
            if (holi.getDay() == now.getDay() && holi.getMonth() == now.getMonth()) {
                offer = new PublicHolidayDiscount(offer);
            }
        }

        driver.setState(State.Busy);
        driver.setCurrentOffer(offer);

        return true;
    }

    public void listAllUsers() {
        for (Map.Entry<String, User> e : userDatabase.entrySet()) {
            System.out.println(e.getValue());
            System.out.println();
        }
        System.out.println();
    }
    public ArrayList<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<User>();
        for (Map.Entry<String, User> e : userDatabase.entrySet()) {
            users.add(e.getValue());
        }
        return users;
    }

    public boolean listPendingDrivers() {
        if (pendingDrivers.size() == 0) {
            System.out.println("no pending drivers exist");
            return false;
        }
        for (Driver pendingDriver : pendingDrivers) System.out.println(pendingDriver);
        System.out.println();
        return true;
    }

    public void addUser(User user) {
        userDatabase.put(user.getUserData().getUserName(), user);
    }

    public ArrayList<Driver> getPendingDrivers() {
        return pendingDrivers;
    }
}
