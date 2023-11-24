package com.example.rental.domain.model.vo;

import com.example.rental.domain.model.RentalItem;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ReturnItem {

    private final RentalItem rentalItem;
    private final LocalDate returnDate;

    @Builder
    public ReturnItem(RentalItem rentalItem, LocalDate returnDate) {
        this.rentalItem = rentalItem;
        this.returnDate = returnDate;
    }

    public static ReturnItem createReturnItem(RentalItem rentalItem, LocalDate returnDate) {
        return new ReturnItem(rentalItem, returnDate);
    }
}
