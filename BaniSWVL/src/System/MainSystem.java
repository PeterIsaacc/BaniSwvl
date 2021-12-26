package System;

import Rides.RideRequest;
import Users.*;
import java.util.ArrayList;

public interface MainSystem {

    public User getUser(String userName);

    public User login(String username, String password);

    public User register(Info data);

    public void addAdmin(User user);

    public boolean notifyDrivers(RideRequest rideRequest);

    public boolean rateAdriver(UserRating userRating, String driverUserName);

    public boolean addAreaToDriver(String area, Driver driver);

    public boolean driverMakingOffer(Driver driver, int index, double price);

    public void listAllUsers();

    public boolean listPendingDrivers();

    public void addUser(User user);

    public ArrayList<Driver> getPendingDrivers();
}
