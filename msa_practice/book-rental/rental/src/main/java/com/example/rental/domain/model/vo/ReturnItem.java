package com.example.rental.domain.model.vo;

import com.example.rental.domain.model.RentalItem;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class ReturnItem {

    private final RentalItem rentalItem;
    private final LocalDate returnDate;

    public static ReturnItem createReturnItem(RentalItem rentalItem, LocalDate returnDate) {
        return new ReturnItem(rentalItem, returnDate);
    }
}
