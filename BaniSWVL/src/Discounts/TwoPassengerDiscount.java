package Discounts;

import Rides.Offer;

public class TwoPassengerDiscount extends DiscountDecorator {
    public TwoPassengerDiscount(Offer offer) {
        super(offer);
    }

    @Override
    public double getPrice() {
        double value = getPrice();
        return value - (value * 0.05);
    }
}
