package com.example.refactoring._23_refused_bequest;

public class Salesman extends Employee {

    protected Quota quota;

    protected Quota getQuota() {
        return new Quota();
    }
}

