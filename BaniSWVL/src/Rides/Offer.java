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

    public double getDriverRating() {
        return driverRating;
    }

    public void setDriverRating(double driverRating) {
        this.driverRating = driverRating;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public RideRequest getRideRequest() {
        return rideRequest;
    }

    public double getPrice() {
        return price;
    }

    public String getDriverUserName() {
        return driverUserName;
    }

    public String toString()
    {
        return (rideRequest + "\nPrice: " + price + "\ndriver username: " + driverUserName + "\ndriver rating: " + driverRating);
    }
}
