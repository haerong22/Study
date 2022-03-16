package _03_stream;

import java.util.Optional;

public class StreamEx_10 {

    public static void main(String[] args) {
        if (isVegetarianFriendlyMenu()) {
            System.out.println("Vegetarian friendly");
        }

        System.out.println(isHealthyMenu());
        System.out.println(isHealthyMenu2());

        findVegetarianDish()
                .ifPresent(dish -> System.out.println(dish.getName()));
    }

    private static Optional<Dish> findVegetarianDish() {
        return Dish.menu.stream().filter(Dish::isVegetarian).findAny();
    }

    private static boolean isHealthyMenu2() {
        return Dish.menu.stream().noneMatch(dish -> dish.getCalories() >= 1000);
    }

    private static boolean isHealthyMenu() {
        return Dish.menu.stream().allMatch(dish -> dish.getCalories() < 1000);
    }

    private static boolean isVegetarianFriendlyMenu() {
        return Dish.menu.stream().anyMatch(Dish::isVegetarian);
    }

}
