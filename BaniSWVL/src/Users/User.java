package Users;

import System.*;

public abstract class User {
    private Info userData;

    public User(Info userData) {
        this.userData = userData;
    }

    public abstract boolean setState(State state);

    public Info getUserData() {
        return userData;
    }

    public abstract User options(MainSystem system);

    public abstract User displayMenu(MainSystem system);

    public abstract String toString();

    public void setUserData(Info userData) {
        this.userData = userData;
    }
}
