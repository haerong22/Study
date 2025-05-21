package stream.basic;

import lambda.lambda5.mystream.MyStreamV3;

import java.util.List;

public class LazyEvalMain2 {

    public static void main(String[] args) {
        List<Integer> data = List.of(1, 2, 3, 4, 5, 6);
        ex1(data);
        ex2(data);
    }

    /*
        === MyStreamV3 시작 ===
        filter() 실행: 1(false)
        filter() 실행: 2(true)
        filter() 실행: 3(false)
        filter() 실행: 4(true)
        filter() 실행: 5(false)
        filter() 실행: 6(true)
        map() 실행: 2 -> 20
        map() 실행: 4 -> 40
        map() 실행: 6 -> 60
        === MyStreamV3 종료 ===


        === Stream API 시작 ===
        === Stream API 종료 ===
     */

    private static void ex1(List<Integer> data) {
        System.out.println("=== MyStreamV3 시작 ===");
        MyStreamV3.of(data)
                .filter(i -> {
                    boolean isEven = i % 2 == 0;
                    System.out.println("filter() 실행: " + i + "(" + isEven + ")");
                    return isEven;
                })
                .map(i -> {
                    int mapped = i * 10;
                    System.out.println("map() 실행: " + i + " -> " + mapped);
                    return mapped;
                });

        System.out.println("=== MyStreamV3 종료 ===");
    }

    private static void ex2(List<Integer> data) {
        System.out.println("=== Stream API 시작 ===");
        data.stream()
                .filter(i -> {
                    boolean isEven = i % 2 == 0;
                    System.out.println("filter() 실행: " + i + "(" + isEven + ")");
                    return isEven;
                })
                .map(i -> {
                    int mapped = i * 10;
                    System.out.println("map() 실행: " + i + " -> " + mapped);
                    return mapped;
                });

        System.out.println("=== Stream API 종료 ===");
    }

}
