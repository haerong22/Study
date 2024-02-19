package com.example.refactoring._17_method_chains._37_hide_delegate;

public class Person {

    private String name;

    private Department department;

    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    Person getManager() {
        return getDepartment().getManager();
    }
}
