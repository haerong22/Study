package com.example.rental.domain.model;

import com.example.rental.domain.model.vo.Item;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class RentalItem {

    private final Item item;
    private final LocalDate rentDate;
    private final boolean overdue;
    private final LocalDate overdueDate;

    @Builder
    private RentalItem(Item item, LocalDate rentDate, boolean overdue, LocalDate overdueDate) {
        this.item = item;
        this.rentDate = rentDate;
        this.overdue = overdue;
        this.overdueDate = overdueDate;
    }

    public static RentalItem createRentalItem(Item item, LocalDate rentDate) {
        return RentalItem.builder()
                .item(item)
                .rentDate(rentDate)
                .overdue(false)
                .overdueDate(rentDate.plusDays(14))
                .build();
    }
}
