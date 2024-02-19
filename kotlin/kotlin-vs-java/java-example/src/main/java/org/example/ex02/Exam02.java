package org.example.ex02;

/**
 * Null 안정성
 */
public class Exam02 {

    private int a;

    public Exam02() {

        var b = 20;
        int c = 30;
        int d;

        Integer e = null;
        Integer f = 20;

        callFunction(a);
        callFunction(b);
        callFunction(c);
//        System.out.println(d);
        callFunction(e); // null point exception
        callFunction(f);
    }

    private void callFunction(Integer i) {
        var temp = i == null ? "null 값 입니다." : i;
        System.out.println(temp);
    }

    public static void main(String[] args) {
        new Exam02();
    }
}
