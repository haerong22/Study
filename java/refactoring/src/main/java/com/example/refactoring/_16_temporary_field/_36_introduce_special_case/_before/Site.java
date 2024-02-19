package com.example.refactoring._16_temporary_field._36_introduce_special_case._before;

public class Site {

    private Customer customer;

    public Site(Customer customer) {
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }
}
