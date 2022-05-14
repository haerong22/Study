package com.example.effectivejava.chapter01.item03.functionalinterface;

@FunctionalInterface
public interface MyFunction {

    String valueOf(Integer integer);

    static String hello() {
        return "hello";
    }
}