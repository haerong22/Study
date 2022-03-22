package com.example.refactoring._10_data_clumps._before;

public class Office {

    private String location;

    private String officeAreCode;

    private String officeNumber;

    public Office(String location, String officeAreCode, String officeNumber) {
        this.location = location;
        this.officeAreCode = officeAreCode;
        this.officeNumber = officeNumber;
    }

    public String officePhoneNumber() {
        return officeAreCode + "-" + officeNumber;
    }

    public String getOfficeAreCode() {
        return officeAreCode;
    }

    public void setOfficeAreCode(String officeAreCode) {
        this.officeAreCode = officeAreCode;
    }

    public String getOfficeNumber() {
        return officeNumber;
    }

    public void setOfficeNumber(String officeNumber) {
        this.officeNumber = officeNumber;
    }
}
