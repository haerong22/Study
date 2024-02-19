package com.example.refactoring._17_method_chains._37_hide_delegate._before;

public class Department {

    private String chargeCode;

    private Person manager;

    public Department(String chargeCode, Person manager) {
        this.chargeCode = chargeCode;
        this.manager = manager;
    }

    public String getChargeCode() {
        return chargeCode;
    }

    public Person getManager() {
        return manager;
    }
}
