package Discounts;

import Rides.Offer;

public class FirstRideDiscount extends DiscountDecorator {

    public FirstRideDiscount(Offer offer) {
        super(offer);
        this.offer = offer;
    }

    @Override
    public double getPrice() {
        double value = offer.getOffer().getPrice();
        double dis = getDiscount();
        return value - (value * dis);
    }

    @Override
    public double getDiscount() {
        return offer.getDiscount() + 0.1;
    }

    @Override
    public String toString() {
        return (offer.toString() + "\nwith a 10% discount from FirstRideDiscount");
    }
}
