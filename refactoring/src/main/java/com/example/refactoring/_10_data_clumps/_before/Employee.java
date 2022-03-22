package com.example.refactoring._10_data_clumps._before;

public class Employee {

    private String name;

    private String personalAreaCode;

    private String personalNumber;

    public Employee(String name, String personalAreaCode, String personalNumber) {
        this.name = name;
        this.personalAreaCode = personalAreaCode;
        this.personalNumber = personalNumber;
    }

    public String personalPhoneNumber() {
        return personalAreaCode + "-" + personalNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPersonalAreaCode() {
        return personalAreaCode;
    }

    public void setPersonalAreaCode(String personalAreaCode) {
        this.personalAreaCode = personalAreaCode;
    }

    public String getPersonalNumber() {
        return personalNumber;
    }

    public void setPersonalNumber(String personalNumber) {
        this.personalNumber = personalNumber;
    }
}
