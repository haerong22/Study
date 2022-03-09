package com.example.refactoring._06_mutable_data._19_separate_query_from_modifier.before;

public class EmailGateway {
    public void send(String bill) {
        System.out.println(bill);
    }
}
