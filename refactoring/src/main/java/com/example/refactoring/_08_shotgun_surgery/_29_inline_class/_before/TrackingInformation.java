package com.example.refactoring._08_shotgun_surgery._29_inline_class._before;

public class TrackingInformation {

    private String shippingCompany;

    private String trackingNumber;

    public TrackingInformation(String shippingCompany, String trackingNumber) {
        this.shippingCompany = shippingCompany;
        this.trackingNumber = trackingNumber;
    }

    public String display() {
        return this.shippingCompany + ": " + this.trackingNumber;
    }

    public String getShippingCompany() {
        return shippingCompany;
    }

    public void setShippingCompany(String shippingCompany) {
        this.shippingCompany = shippingCompany;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }
}
