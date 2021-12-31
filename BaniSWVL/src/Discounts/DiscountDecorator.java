package Discounts;

import Rides.Offer;
import Rides.RideRequest;

public abstract class DiscountDecorator extends Offer{

    public DiscountDecorator(RideRequest rideRequest, double price, String driverUserName, double driverRating) {
        super(rideRequest, price, driverUserName, driverRating);
    }

    public Offer getOffer() {
        return offer.getOffer();
    }

    public abstract double getPrice();
}
