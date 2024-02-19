package com.example.refactoring._11_primitive_obsession._31_replace_type_code_with_subclasses.direct_inheritance;

public class Engineer extends Employee {

    public Engineer(String name) {
        super(name);
    }

    @Override
    public String getType() {
        return "engineer";
    }
}
