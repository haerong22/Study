package org.example.ex03;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 가변 불변 컬렉션
 */
public class Exam03 {

    public Exam03() {

        // 가변
        var list1 = new ArrayList<User>();
        list1.add(new User("a", 10));
        list1.add(new User("b", 20));
        list1.add(new User("c", 30));

        // 원소 추가/삭제 불가, 변경 가능
        var list2 = Arrays.asList(
                new User("e", 10),
                new User("f", 20),
                new User("g", 30)
        );

        // 불변
        var list3 = List.of(
                new User("h", 10),
                new User("i", 20),
                new User("j", 30)
        );

        list1.forEach(System.out::println);

        for (int i = 0; i < list2.size(); i++) {
            System.out.println(list2.get(i));
        }

        for (User user : list3) {
            System.out.println(user);
        }
    }

    public static void main(String[] args) {
        new Exam03();
    }
}

class User {
    private String name;
    private int age;

    public User() {
    }

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}