package com.example.rental.domain.model.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RentalCardNoTest {

    @Test
    @DisplayName("RentalCardNo를 생성한다.")
    void createRentalCardNo() {
        // given

        // when
        RentalCardNo result = RentalCardNo.createRentalCardNo();

        // then
        assertThat(result).isNotNull();
    }

}