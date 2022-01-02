package com.example.Rides;

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
        this.rideRequest = null;
        this.price = 0;
        this.driverRating = 0;
        this.driverUserName = null;
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
        return (rideRequest + ", Original Price: " + price + ", driver username: " + driverUserName + ", driver rating: " + driverRating);
    }

    public Offer getOffer() {
        return this;
    }
}
