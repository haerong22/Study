package com.example.refactoring._20_large_class._41_extract_superclass;

import java.util.List;

public class Department extends Party {

    private List<Employee> staff;

    public Department(String name) {
        super(name);
    }

    @Override
    public double monthlyCost() {
        return this.staff.stream().mapToDouble(Employee::monthlyCost).sum();
    }

    public List<Employee> getStaff() {
        return staff;
    }

    public int headCount() {
        return this.staff.size();
    }
}
