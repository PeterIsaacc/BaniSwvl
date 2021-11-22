import java.util.ArrayList;
import java.util.Map;

public class admin extends User{
    ArrayList<Driver> pendingDrivers;
    public admin(Info userData) {
        super(userData);
    }

    public void verfiyDriverRegistration(String driverUserName)
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
    public void listPendingDrivers()
    {
        if(pendingDrivers.size() == 0)
        {
            System.out.println("no pending drivers exist");
            return;
        }
        for(int i = 0; i < pendingDrivers.size(); i++)
            System.out.println(pendingDrivers.get(i).getUserData().getUserName());
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
    public void menu(){
        System.out.println("this is admin");
    }
}
