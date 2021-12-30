package Discounts;

import Rides.Offer;

public class AreaDiscount extends DiscountDecorator {
    public AreaDiscount(Offer offer) {
        super(offer);
    }

    @Override
    public double getPrice() {
        double value = getPrice();
        return value - (value * 0.1);
    }
}
