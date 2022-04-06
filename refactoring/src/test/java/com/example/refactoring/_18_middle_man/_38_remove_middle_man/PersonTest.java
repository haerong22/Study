package com.example.refactoring._18_middle_man._38_remove_middle_man;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {

    @Test
    void getManager() {
        Person kim = new Person("kim", null);
        Person lee = new Person("lee", new Department(kim));
        assertEquals(kim, lee.getDepartment().getManager());
    }

}