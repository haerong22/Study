package com.example.refactoring._11_primitive_obsession._31_replace_type_code_with_subclasses.indirect_inheritance;

public class EmployeeType {
    public String capitalizedType() {
        return this.toString().substring(0, 1).toUpperCase() + this.toString().substring(1).toLowerCase();
    }
}
