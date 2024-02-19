package com.example.basic.singleton;

public class SingletonService {

    private static final SingletonService instance = new SingletonService();

    private SingletonService() {}

    public static SingletonService getInstance() {
        return instance;
    }

    public void logic() {
        System.out.println("싱클톤 객체 호출");
    }
}
