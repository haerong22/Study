package com.example.rental.domain.model;

import com.example.rental.domain.model.vo.Item;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class RentalItemTest {

    @Test
    @DisplayName("RentalItem을 생성할 수 있다.")
    void createRentalItem() {
        // given
        LocalDate now = LocalDate.of(2023, 11, 22);
        Item item = Item.builder()
                .no(10L)
                .title("SpringBoot")
                .build();

        // when
        RentalItem result = RentalItem.createRentalItem(item, now);

        // then
        assertThat(result).isNotNull()
                .extracting("rentDate", "overdue", "overdueDate")
                .containsExactly(now, false, now.plusDays(14));

        assertThat(result.getItem()).isNotNull()
                .extracting("no", "title")
                .containsExactly(10L, "SpringBoot");
    }

    @Test
    @DisplayName("대여도서를 연체 처리할 수 있다.")
    void overdue() {
        // given
        LocalDate now = LocalDate.of(2023, 11, 22);

        Item item = Item.builder()
                .no(10L)
                .title("SpringBoot")
                .build();

        RentalItem rentalItem = RentalItem.createRentalItem(item, now);

        // when
        rentalItem.overdue();

        // then
        assertThat(rentalItem.isOverdue()).isTrue();
    }

}