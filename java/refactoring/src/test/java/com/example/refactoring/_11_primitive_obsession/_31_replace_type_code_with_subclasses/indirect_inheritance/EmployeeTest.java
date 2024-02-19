package com.example.refactoring._11_primitive_obsession._31_replace_type_code_with_subclasses.indirect_inheritance;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    @Test
    void capitalizedType() {
        assertEquals("Manager", new FullTimeEmployee("kim", "manager").capitalizedType());
        assertEquals("Engineer", new PartTimeEmployee("lee", "engineer").capitalizedType());
        assertThrows(IllegalArgumentException.class, () -> new Employee("park", "wrong type"));
    }

}