package org.example.ex06;

/**
 * 클래스
 */
public class Exam06 {

    public Exam06() {
        Animal dog = new Dog("쿠키");
        System.out.println(dog.getAge());
        dog.setAge(10);
        System.out.println(dog.getName());

        dog.bark();
        dog.eat();
    }

    public static void main(String[] args) {
        new Exam06();
    }
}

interface Bark {
    void bark();
}
abstract class Animal implements Bark {

    private final String name;
    private int age;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Animal(String name) {
        this.name = name;
    }

    public void eat() {
        System.out.println(name + " 식사 시작~");
    }

    public String getName() {
        return name;
    }
}

class Dog extends Animal {

    public Dog(String name) {
        super(name);
    }

    @Override
    public void bark() {
        System.out.println("멍멍");
    }
}
