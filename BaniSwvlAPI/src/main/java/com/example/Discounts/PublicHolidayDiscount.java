package com.example.Discounts;

import com.example.Rides.Offer;

public class PublicHolidayDiscount extends DiscountDecorator {
    public PublicHolidayDiscount(Offer offer) {
        super(offer);
        this.offer = offer;
    }

    @Override
    public double getPrice() {
        double value = offer.getPrice();
        return value - (value * 0.05);
    }
    @Override
    public String toString(){
        return (offer.toString() + "\nwith a 5% discount from PublicHolidayDiscount");
    }
}
