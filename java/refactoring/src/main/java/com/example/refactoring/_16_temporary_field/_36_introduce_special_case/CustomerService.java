package com.example.refactoring._16_temporary_field._36_introduce_special_case;

public class CustomerService {

    public String customerName(Site site) {
        return site.getCustomer().getName();
    }

    public BillingPlan billingPlan(Site site) {
       return site.getCustomer().getBillingPlan();
    }

    public int weeksDelinquent(Site site) {
        return site.getCustomer().getPaymentHistory().getWeeksDelinquentInLastYear();
    }

}
