package Discounts;

import Rides.*;

public abstract class DiscountDecorator extends Offer{
    Offer offer;

    public DiscountDecorator(RideRequest rideRequest, double price, String driverUserName, double driverRating) {
        super(rideRequest, price, driverUserName, driverRating);
    }

    public abstract void adjustPrice();
}
