package com.example.rental.domain.model;

import com.example.rental.domain.model.vo.Item;
import com.example.rental.domain.model.vo.ReturnItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

class RentalCardTest {

    @Test
    @DisplayName("대여품목을 추가할 수 있다.")
    void addRentalItem() {
        // given
        RentalCard rentalCard = RentalCard.builder()
                .rentalItems(new ArrayList<>())
                .build();
        Item item = Item.builder().build();
        LocalDate rentDate = LocalDate.of(2023, 11, 22);
        RentalItem rentalItem = RentalItem.createRentalItem(item, rentDate);

        // when
        rentalCard.addRentalItem(rentalItem);

        // then
        assertThat(rentalCard.getRentalItems()).hasSize(1);
    }

    @Test
    @DisplayName("대여품목을 삭제할 수 있다.")
    void removeRentalItem() {
        // given
        Item item = Item.builder().build();
        LocalDate rentDate = LocalDate.of(2023, 11, 22);
        RentalItem rentalItem = RentalItem.createRentalItem(item, rentDate);

        RentalCard rentalCard = RentalCard.builder()
                .rentalItems(new ArrayList<>() {{
                    add(rentalItem);
                }})
                .build();

        // when
        rentalCard.removeRentalItem(rentalItem);

        // then
        assertThat(rentalCard.getRentalItems()).isEmpty();
    }

    @Test
    @DisplayName("반납품목을 추가할 수 있다.")
    void addReturnItem() {
        // given
        RentalCard rentalCard = RentalCard.builder()
                .returnItems(new ArrayList<>())
                .build();

        RentalItem rentalItem = RentalItem.createRentalItem(
                Item.builder().build(), LocalDate.of(2023, 11, 22)
        );
        ReturnItem returnItem = ReturnItem.createReturnItem(
                rentalItem, LocalDate.of(2023, 11, 22)
        );

        // when
        rentalCard.addReturnItem(returnItem);

        // then
        assertThat(rentalCard.getReturnItems()).hasSize(1);
    }

    @Test
    @DisplayName("판납품목을 삭제할 수 있다.")
    void removeReturnItem() {
        // given
        RentalItem rentalItem = RentalItem.createRentalItem(
                Item.builder().build(), LocalDate.of(2023, 11, 22)
        );
        ReturnItem returnItem = ReturnItem.createReturnItem(
                rentalItem, LocalDate.of(2023, 11, 22)
        );

        RentalCard rentalCard = RentalCard.builder()
                .returnItems(new ArrayList<>() {{
                    add(returnItem);
                }})
                .build();

        // when
        rentalCard.removeReturnItem(returnItem);

        // then
        assertThat(rentalCard.getReturnItems()).isEmpty();
    }

}