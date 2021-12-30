package Discounts;

import Rides.Offer;

public class BirthdayDiscount extends DiscountDecorator {
    public BirthdayDiscount(Offer offer) {
        super(offer);
    }

    @Override
    public double getPrice() {
        double value = getPrice();
        return value - (value * 0.1);
    }
}
