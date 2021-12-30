package Discounts;

import Rides.Offer;

public class FirstRideDiscount extends DiscountDecorator{

    public FirstRideDiscount(Offer offer) {
        super(offer);
    }

    @Override
    public double getPrice() {
        double value = getPrice();
        return value - (value * 0.1);
    }
}
