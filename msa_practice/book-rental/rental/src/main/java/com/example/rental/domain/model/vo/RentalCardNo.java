package com.example.rental.domain.model.vo;

import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
public class RentalCardNo {

    private final String no;

    private RentalCardNo(String no) {
        this.no = no;
    }

    public static RentalCardNo createRentalCardNo() {
        UUID uuid = UUID.randomUUID();
        String year = String.valueOf(LocalDate.now().getYear());
        return new RentalCardNo(year + '-' + uuid);
    }

}
