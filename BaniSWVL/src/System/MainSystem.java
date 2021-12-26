package System;

import Rides.RideRequest;
import Users.*;

import java.util.ArrayList;

public interface MainSystem {

    User getUser(String userName);

    User login(String username, String password);

    User register(Info data);

    void addAdmin(User user);

    boolean notifyDrivers(RideRequest rideRequest);

    boolean rateDriver(UserRating userRating, String driverUserName);

    boolean addAreaToDriver(String area, Driver driver);

    boolean driverMakingOffer(Driver driver, int index, double price);

    void listAllUsers();

    boolean listPendingDrivers();

    void addUser(User user);

    ArrayList<Driver> getPendingDrivers();
}
