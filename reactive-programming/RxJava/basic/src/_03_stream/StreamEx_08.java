package _03_stream;

import java.util.List;

public class StreamEx_08 {

    public static void main(String[] args) {

        // skip(n) n번째 요소 이후의 요소만 선택, 없으면 빈배열
        List<Dish> dishes = Dish.menu.stream()
                .filter(dish -> dish.getCalories() > 300)
                .skip(2)
                .toList();

        System.out.println("dishes = " + dishes);
    }
}
