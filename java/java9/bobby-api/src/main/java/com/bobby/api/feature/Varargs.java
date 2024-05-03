package com.bobby.api.feature;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * - 가변인자 : 메소드를 호출할 때 매개변수의 개수를 자유롭게 넣어 해당 값들이 담겨있는 배열을 받는다.
 * - 제네릭 함수에서 가변 인자를 사용 시 경고
 * 	-> 상위 타입으로 형 변환 후 추가 작업을 할 경우 에러가 발생 할 수 있기 때문에 안전하지 않은 코드이다.
 * 	- @SafeVarargs 이용해 경고 제거
 */
public class Varargs {

    @SafeVarargs
    public static <T> List<T> flatten(List<T>... lists) {
        List<T> result = new ArrayList<>();

        for (List<T> list : lists) {
            result.addAll(list);
        }
        return result;
    }

    public static void main(String[] args) {
        List<Integer> list1 = Arrays.asList(1, 2, 3);
        List<Integer> list2 = Arrays.asList(4, 5);
        List<Integer> result = flatten(list1, list2);
        System.out.println(result);
    }
}
