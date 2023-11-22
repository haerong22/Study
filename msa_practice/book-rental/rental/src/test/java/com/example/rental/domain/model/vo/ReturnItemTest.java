package com.example.rental.domain.model.vo;

import com.example.rental.domain.model.RentalItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class ReturnItemTest {

    @Test
    @DisplayName("ReturnItem을 생성할 수 있다.")
    void createReturnItem() {
        // given
        LocalDate returnDate = LocalDate.of(2023, 11, 22);
        RentalItem rentalItem = RentalItem.builder()
                .build();

        // when
        ReturnItem result = ReturnItem.createReturnItem(rentalItem, returnDate);

        // then
        assertThat(result.getRentalItem()).isEqualTo(rentalItem);
        assertThat(result.getReturnDate()).isEqualTo(returnDate);
    }

}