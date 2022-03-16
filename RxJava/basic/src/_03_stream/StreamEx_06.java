package _03_stream;

import java.util.List;

public class StreamEx_06 {

    public static void main(String[] args) {

        List<Dish> vegetarianMenu = Dish.menu.stream()
                .filter(Dish::isVegetarian)
                .toList();

        System.out.println("vegetarianMenu = " + vegetarianMenu);

    }
}
