
public abstract class  User {
    private Info userData;

    public User(Info userData) {
        this.userData = userData;
    }

    public User() {
        this.userData = null;
    }


    public Info getUserData() {
        return userData;
    }

    public abstract void menu();

    public void setUserData(Info userData) {
        this.userData = userData;
    }
}
