package com.example.Discounts;

import com.example.Rides.Offer;

public class AreaDiscount extends DiscountDecorator {
    public AreaDiscount(Offer offer) {
        super(offer);
        this.offer = offer;
    }

    @Override
    public double getPrice() {
        double value = offer.getPrice();
        return value - (value * 0.1);
    }

    @Override
    public String toString() {
        return (offer.toString() + "\nwith a 10% discount from AreaDiscount");
    }
}
