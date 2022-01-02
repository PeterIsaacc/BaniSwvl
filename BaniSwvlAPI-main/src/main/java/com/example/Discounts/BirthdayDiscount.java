package com.example.Discounts;

import com.example.Rides.Offer;

public class BirthdayDiscount extends DiscountDecorator {
    public BirthdayDiscount(Offer offer) {
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
        return (offer.toString() + "\nwith a 10% discount from BirthdayDiscount");
    }
}
