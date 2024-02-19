package com.example.effectivejava.chapter01.item03.methodreference;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Person {

    LocalDate birthday;

    public Person() {

    }

    public Person(LocalDate birthday) {
        this.birthday = birthday;
    }

    public static int compareByAge(Person a, Person b) {
        return a.birthday.compareTo(b.birthday);
    }

    public int compareByAge2(Person a, Person b) {
        return a.birthday.compareTo(b.birthday);
    }

    // 임의 객체 참조의 경우 첫번째 인자는 자기 자신
    public int compareByAge3(Person b) {
        return this.birthday.compareTo(b.birthday);
    }


    public static void main(String[] args) {
        List<Person> people = new ArrayList<>();
        people.add(new Person(LocalDate.of(1982, 7, 15)));
        people.add(new Person(LocalDate.of(2011, 3, 2)));
        people.add(new Person(LocalDate.of(2013, 1, 28)));

        Person person = new Person(LocalDate.of(2022, 5, 14));

        people.sort(Person::compareByAge);
        people.sort(person::compareByAge2);
        people.sort(Person::compareByAge3);
    }

    public int getAge() {
        return LocalDate.now().getYear() - birthday.getYear();
    }

}