import java.util.HashMap;
import java.util.Map;

public class system {
    Map<String, User> userDatabase;

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
        if (data instanceof driverInfo) newUser = new Driver(data);
        else newUser = new Client(data);

        return userDatabase.put(data.getUserName(), newUser);
    }
}
