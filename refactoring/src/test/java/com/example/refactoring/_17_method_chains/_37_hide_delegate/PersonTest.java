package com.example.refactoring._17_method_chains._37_hide_delegate;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {

    @Test
    void manager() {
        Person kim = new Person("kim");
        Person nick = new Person("nick");
        kim.setDepartment(new Department("m365deploy", nick));

        Person manager = kim.getManager();
        assertEquals(nick, manager);
    }

}