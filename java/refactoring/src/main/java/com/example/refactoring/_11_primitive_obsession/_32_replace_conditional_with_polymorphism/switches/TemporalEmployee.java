package com.example.refactoring._11_primitive_obsession._32_replace_conditional_with_polymorphism.switches;

import java.util.List;

public class TemporalEmployee extends Employee {

    public TemporalEmployee(List<String> availableProjects) {
        super(availableProjects);
    }

    @Override
    public int vacationHours() {
        return 32;
    }

}
