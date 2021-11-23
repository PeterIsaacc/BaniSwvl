package Users;
import java.util.ArrayList;
import java.util.Map;

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
    public String toString()
    {
        String string = "--Admin--\n";
        string+=this.getUserData().toString();
        return string;
    }
}
