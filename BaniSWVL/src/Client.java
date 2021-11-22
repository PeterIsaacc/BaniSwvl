public class Client extends User {
    private State state;

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Client(Info userData) {
        super(userData);
    }
    public void menu(){
        System.out.println("this is client");
    }
    public RideRequest rideRequest(String source, String destination)
    {
        return new RideRequest(source,destination, getUserData().getUserName());
    }
    public UserRating rateDriver(int rating, String comment)
    {
        return new UserRating(getUserData().getUserName(), rating, comment);
    }
}


