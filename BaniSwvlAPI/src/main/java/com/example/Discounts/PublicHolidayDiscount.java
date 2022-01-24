package Discounts;

import Rides.Offer;

public class PublicHolidayDiscount extends DiscountDecorator {
    public PublicHolidayDiscount(Offer offer) {
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
        return offer.getDiscount() + 0.05;
    }

    @Override
    public String toString() {
        return (offer.toString() + "\nwith a 5% discount from PublicHolidayDiscount");
    }
}
