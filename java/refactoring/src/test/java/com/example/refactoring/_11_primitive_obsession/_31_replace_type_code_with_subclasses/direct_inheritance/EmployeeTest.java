package com.example.refactoring._11_primitive_obsession._31_replace_type_code_with_subclasses.direct_inheritance;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    @Test
    void employeeType() {
        assertEquals("engineer", Employee.createEmployee("kim", "engineer").getType());
        assertEquals("manager", Employee.createEmployee("lee", "manager").getType());
        assertThrows(IllegalArgumentException.class, () -> Employee.createEmployee("park", "wrong type"));
    }

}