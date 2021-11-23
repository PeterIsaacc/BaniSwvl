package Rides;

public class Offer {
    private RideRequest rideRequest;
    private double price;
    private double driverRating;
    private String driverUserName;
    public Offer(RideRequest rideRequest, double price, String driverUserName, double driverRating) {
        this.rideRequest = rideRequest;
        this.price = price;
        this.driverUserName = driverUserName;
        this.driverRating = driverRating;
    }

    public RideRequest getRideRequest() {
        return rideRequest;
    }

    public double getPrice() {
        return price;
    }
    public String toString()
    {
        return (rideRequest + "\nPrice: " + price + "\ndriver username: " + driverUserName + "\ndriver rating: " + driverRating);
    }
}
