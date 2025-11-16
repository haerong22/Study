package org.example.learningtest.archunit.adapter;

import org.example.learningtest.archunit.application.MyService;

public class MyAdapter {
    MyService myService;

    void run() {
        myService = new MyService();
        System.out.println(myService);
    }
}
