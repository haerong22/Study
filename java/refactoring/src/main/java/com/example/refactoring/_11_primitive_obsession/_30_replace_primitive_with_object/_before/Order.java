package com.example.refactoring._11_primitive_obsession._30_replace_primitive_with_object._before;

public class Order {

    private String priority;

    public Order(String priority) {
        this.priority = priority;
    }

    public String getPriority() {
        return priority;
    }
}
