package com.example.refactoring._06_mutable_data._20_remove_setting_method;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {

    @Test
    void person() {
        Person person = new Person(10);

        person.setName("john");
        assertEquals(10, person.getId());
        assertEquals("john", person.getName());
        person.setName("jane");
        assertEquals("jane", person.getName());
    }
}