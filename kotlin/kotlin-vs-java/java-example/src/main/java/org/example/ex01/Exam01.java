package org.example.ex01;

/**
 * 변수 선언
 */
public class Exam01 {

    public Exam01() {
        int age = 10;
        Integer _age = 20;

        double d = 10d;
        Double _d = 20d;

        float f = 20f;
        Float _f = 20f;

        long l = 10L;
        Long _l = 10L;

        boolean b = true;
        Boolean _b = true;

        String name = "홍길동";

        System.out.printf("사용자 이름: %s", name);
    }

    public static void main(String[] args) {
        new Exam01();
    }
}
