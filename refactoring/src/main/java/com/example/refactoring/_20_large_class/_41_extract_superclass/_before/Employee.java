package com.example.refactoring._20_large_class._41_extract_superclass._before;

public class Employee {

    private Integer id;

    private String name;

    private double monthlyCost;

    public double annualCost() {
        return this.monthlyCost * 12;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getMonthlyCost() {
        return monthlyCost;
    }
}
