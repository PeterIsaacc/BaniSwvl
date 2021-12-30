package Discounts;

import Rides.Offer;

public class PublicHolidayDiscount extends DiscountDecorator {
    public PublicHolidayDiscount(Offer offer) {
        super(offer);
    }

    @Override
    public double getPrice() {
        double value = getPrice();
        return value - (value * 0.05);
    }
}
