package com.example.refactoring._23_refused_bequest._before;

public class Employee {

    protected Quota quota;

    protected Quota getQuota() {
        return new Quota();
    }

}
