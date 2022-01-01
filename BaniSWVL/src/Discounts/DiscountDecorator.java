package Discounts;

import Rides.Offer;


public abstract class DiscountDecorator extends Offer {
    Offer offer;

    public DiscountDecorator(Offer offer) {
        this.offer = offer;
    }

    public Offer getOffer() {
        return offer.getOffer();
    }

    public abstract double getPrice();
}
