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

    public Offer() {
    }

    public double getDiscount(){
        return 0;
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

    public String toString() {
        return (rideRequest + "\nOriginal Price: " + price + "\ndriver username: " + driverUserName + "\ndriver rating: " + driverRating);
    }

    public Offer getOffer() {
        return this;
    }
}
