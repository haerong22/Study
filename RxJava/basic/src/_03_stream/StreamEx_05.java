package _03_stream;

import java.util.List;

public class StreamEx_05 {

    public static void main(String[] args) {

        List<String> names = Dish.menu.stream()
                .filter(dish -> dish.getCalories() > 300)
                .map(Dish::getName)
                .limit(3)
                .toList();

        System.out.println("names = " + names);
    }
}
