package System;

import Log.Log;
import Rides.Offer;
import Rides.RideRequest;
import Users.*;

import java.util.ArrayList;

public interface MainSystem {

    public ArrayList<RideRequest> getRideRequests();

    public boolean checkdriver(Driver d, String Area);

    public void setRideRequests(ArrayList<RideRequest> rideRequests);

    public Log getLogs();

    public void setLogs(Log logs);

    User getUser(String userName);

    User login(String username, String password);

    User register(Info data);

    void addAdmin(User user);

    public boolean checkAreaDiscounts(String area);

    public boolean addAreaDiscounts(String area);

    public boolean clientAcceptOffer(Offer offer, Client client);

    boolean updateSystemRideRequests(RideRequest rideRequest);

    boolean rateDriver(UserRating userRating, String driverUserName);

    boolean addAreaToDriver(String area, Driver driver);

    boolean driverMakingOffer(Driver driver, RideRequest index, double price);

    void listAllUsers();

    boolean listPendingDrivers();

    void addUser(User user);

    ArrayList<Driver> getPendingDrivers();
}
