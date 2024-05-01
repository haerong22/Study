package com.bobby.domain.service;

public class DatabaseStringRepository implements StringRepository {

    @Override
    public void save(String str) {
        System.out.println("데이터베이스에 저장");
    }
}
