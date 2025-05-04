package lambda.lambda5.map;

import java.util.List;

public class MapMainV3 {

    public static void main(String[] args) {
        List<String> list = List.of("1", "12", "123", "1234");

        // 문자열 -> 숫자
        List<Integer> numbers = StringToIntegerMapper.map(list, s -> Integer.valueOf(s));
        System.out.println("numbers = " + numbers);

        // 문자열 길이
        List<Integer> lengths = StringToIntegerMapper.map(list, s -> s.length());
        System.out.println("lengths = " + lengths);
    }
    /*
        numbers = [1, 12, 123, 1234]
        lengths = [1, 2, 3, 4]
     */
}
