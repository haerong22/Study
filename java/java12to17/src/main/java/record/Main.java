package record;

import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {
        FruitDtoV2 fruitDtoV2 = new FruitDtoV2("사과", 2000, LocalDate.of(2024, 5, 20));
        System.out.println(fruitDtoV2.price());
        System.out.println(fruitDtoV2);
    }
}
