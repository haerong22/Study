package com.example.refactoring._06_mutable_data._19_separate_query_from_modifier;

public class Billing {

    private Customer customer;

    private EmailGateway emailGateway;

    public Billing(Customer customer, EmailGateway emailGateway) {
        this.customer = customer;
        this.emailGateway = emailGateway;
    }

    // 어떤 값을 조회하는 메소드에서는 사이드이펙트가 생기지 않도록 조회와 명령을 분리한다.
    public double getTotalOutstanding() {
        return customer.getInvoices().stream()
                .map(Invoice::getAmount)
                .reduce((double) 0, Double::sum);
    }

    public void sendBill() {
        emailGateway.send(formatBill(customer));
    }

    private String formatBill(Customer customer) {
        return "sending bill for " + customer.getName();
    }
}
