import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class system {
    Map<String, User> userDatabase;
    Map<String, ArrayList<String>> AreaToDriverMap;

    public system(Map<String, User> userDatabase) {
        this.userDatabase = userDatabase;
    }

    public system() {
        userDatabase = new HashMap<>();
    }

    public User login(String username, String password) {
        if (userDatabase.get(username).getUserData().getPassword().equals(password))
            return userDatabase.get(username);
        else
            System.out.println("username or password is wrong");
        return null;
    }

    public User register(Info data) {
        if (userDatabase.containsKey(data.getUserName())) {
            System.out.println("username already exists");
            return null;
        }

        User newUser;
        if (data instanceof DriverInfo) newUser = new Driver(data);
        else newUser = new Client(data);

        return userDatabase.put(data.getUserName(), newUser);
    }
    public boolean notifyDrivers(RideRequest rideRequest)
    {
        String area = rideRequest.getSource();
        if(AreaToDriverMap.containsKey(area))
        {
            ArrayList<String> drivers = AreaToDriverMap.get(area);
            for(int i = 0; i < drivers.size(); i++)
            {
                String userName = drivers.get(i);
                Driver driver = (Driver) userDatabase.get(userName);
                driver.addRideRequest(rideRequest);
            }
            return true;
        }
        return false;
    }
    public boolean rateAdriver(UserRating userRating, String driverUserName)
    {
        if(userDatabase.containsKey(driverUserName))
        {
            User user = userDatabase.get(driverUserName);
            if(user instanceof Driver)
            {
                Driver driver = (Driver) user;
                driver.addRating(userRating);
                return true;
            }
            else return false;
        }
        else return false;
    }
    public boolean addAreaToDriver(String area, Driver driver)
    {
        if(driver.containArea(area))
            return false;
        driver.addArea(area);
        String username = driver.getUserData().getUserName();

        if(AreaToDriverMap.containsKey(area))
        {
            ArrayList<String> drivers = new ArrayList<String>();
            drivers.add(username);
            AreaToDriverMap.put(area,drivers);
        }
        else
        {
            AreaToDriverMap.get(area).add(username);
        }
        return true;
    }

}
