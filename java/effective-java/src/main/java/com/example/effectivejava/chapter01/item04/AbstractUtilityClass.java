package com.example.effectivejava.chapter01.item04;

public abstract class AbstractUtilityClass {

    public AbstractUtilityClass() {
        System.out.println("constructor");
    }

    public static String hello() {
        return "hello";
    }

    public static void main(String[] args) {
        String hello = AbstractUtilityClass.hello();

    }
}