package com.bobby.api.reflection;

import com.bobby.domain.Person;

import java.lang.reflect.Field;

public class Main {

    public static void main(String[] args) throws Exception {
        Person person = new Person();

        Class<Person> personClass = Person.class;

        Field w = personClass.getDeclaredField("weight");
        w.setAccessible(true);

        w.set(person, 100);
        System.out.println("person.weight = " + person.getWeight());
    }
}
