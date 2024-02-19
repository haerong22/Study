package com.example.refactoring._11_primitive_obsession._30_replace_primitive_with_object;

public class Order {

    private final Priority priority;

    public Order(String priorityValue) {
        this(new Priority(priorityValue));
    }
    public Order(Priority priority) {
        this.priority = priority;
    }

    public Priority getPriority() {
        return priority;
    }
}
