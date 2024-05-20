package record;

import java.time.LocalDate;

public record FruitDtoV2(
        String name,

        @MyAnnotation
        int price,
        LocalDate date
) {

    private static final double DISCOUNT_RATE = 0.3;

    public int getDiscountPrice() {
        return (int) (price * (1.0 - DISCOUNT_RATE));
    }

    // compact constructor
    public FruitDtoV2 {
        System.out.println("생성자 호출!!");
        if (price < 0 ) {
            throw new IllegalArgumentException("과일의 가격은 양수 입니다.");
        }
    }
}
