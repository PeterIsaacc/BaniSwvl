import java.util.ArrayList;

public class Client extends User {
    private State state;
    private ArrayList<Offer> offers;
    private ArrayList<RideRequest> rideRequests;
    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Client(Info userData) {
        super(userData);
        rideRequests = new ArrayList<RideRequest>();
        offers = new ArrayList<Offer>();
        state = State.Accepted;
    }
    public RideRequest rideRequest(String source, String destination)
    {
        RideRequest rideRequest = new RideRequest(source,destination, getUserData().getUserName());
        rideRequests.add(rideRequest);
        return rideRequest;
    }
    public UserRating rateDriver(int rating, String comment)
    {
        return new UserRating(getUserData().getUserName(), rating, comment);
    }
    public void addOffer(Offer offer)
    {
        offers.add(offer);
    }
    public void addRideRequest(RideRequest rideRequest)
    {
        rideRequests.add(rideRequest);
    }
    public void listRideRequests(){
        if(rideRequests.size() == 0) {
            System.out.println("no rides exist yet");
            return;
        }
        for(int i = 0; i < rideRequests.size(); i++)
        {
            System.out.println(rideRequests.get(i));
        }
    }
    public void listOffers()
    {
        if(offers.size() == 0)
        {
            System.out.println("no offers exist yet");
            return;
        }
        for (int i = 0; i < offers.size(); i++)
        {
            System.out.println(offers.get(i));
        }
    }
    public String toString()
    {
        String string = "--Client--\n";
        string+=this.getUserData().toString();
        string+=("\nState: " + state);
        return string;
    }
}


