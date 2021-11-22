public class Offer {
    private RideRequest rideRequest;
    private double price;

    public Offer(RideRequest rideRequest, double price) {
        this.rideRequest = rideRequest;
        this.price = price;
    }

    public RideRequest getRideRequest() {
        return rideRequest;
    }

    public double getPrice() {
        return price;
    }
}
