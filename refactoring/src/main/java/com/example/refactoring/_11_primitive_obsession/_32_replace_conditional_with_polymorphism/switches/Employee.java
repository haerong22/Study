package com.example.refactoring._11_primitive_obsession._32_replace_conditional_with_polymorphism.switches;

import java.util.List;

public abstract class Employee {

    protected List<String> availableProjects;

    public Employee() {
    }

    public Employee(List<String> availableProjects) {
        this.availableProjects = availableProjects;
    }

    public abstract int vacationHours();

    public boolean canAccessTo(String project) {
        return this.availableProjects.contains(project);
    }
}
