package com.example.effectivejava.chapter01.item01;

import java.util.ArrayList;
import java.util.Comparator;

public class ListQuiz {

    public static void main(String[] args) {
        ArrayList<Integer> numbers = new ArrayList<>();
        numbers.add(200);
        numbers.add(30);
        numbers.add(120);
        numbers.add(2);

        System.out.println("numbers = " + numbers);

        Comparator<Integer> desc = (a, b) -> b - a;
        numbers.sort(desc);

        System.out.println("numbers = " + numbers);

        numbers.sort(desc.reversed());

        System.out.println("numbers = " + numbers);
    }
}
