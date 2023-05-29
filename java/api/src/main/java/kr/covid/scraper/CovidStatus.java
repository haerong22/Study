package kr.covid.scraper;

public class CovidStatus {

    private String region;
    private int total;
    private int domestic;
    private int abroad;
    private int confirmed;
    private int deaths;
    private double rate;

    public CovidStatus() {
    }

    public CovidStatus(String region, int total, int domestic, int abroad, int confirmed, int deaths, double rate) {
        this.region = region;
        this.total = total;
        this.domestic = domestic;
        this.abroad = abroad;
        this.confirmed = confirmed;
        this.deaths = deaths;
        this.rate = rate;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getDomestic() {
        return domestic;
    }

    public void setDomestic(int domestic) {
        this.domestic = domestic;
    }

    public int getAbroad() {
        return abroad;
    }

    public void setAbroad(int abroad) {
        this.abroad = abroad;
    }

    public int getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(int confirmed) {
        this.confirmed = confirmed;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "CovidStatus{" +
                "region='" + region + '\'' +
                ", total=" + total +
                ", domestic=" + domestic +
                ", abroad=" + abroad +
                ", confirmed=" + confirmed +
                ", deaths=" + deaths +
                ", rate=" + rate +
                '}';
    }
}
