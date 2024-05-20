package record;

import java.time.LocalDate;
import java.util.Objects;

public final class FruitDto {
    private final String name;
    private final int price;
    private final LocalDate date;

    public FruitDto(String name, int price, LocalDate date) {
        this.name = name;
        this.price = price;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FruitDto fruitDto = (FruitDto) o;
        return price == fruitDto.price && Objects.equals(name, fruitDto.name) && Objects.equals(date, fruitDto.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, date);
    }

    @Override
    public String toString() {
        return "FruitDto{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", date=" + date +
                '}';
    }
}
