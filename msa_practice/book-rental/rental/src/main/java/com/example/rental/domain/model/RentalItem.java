package com.example.rental.domain.model;

import com.example.rental.domain.model.vo.Item;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@EqualsAndHashCode
public class RentalItem {

    private Item item;
    private LocalDate rentDate;
    private boolean overdue;
    private LocalDate overdueDate;

    @Builder
    public RentalItem(Item item, LocalDate rentDate, boolean overdue, LocalDate overdueDate) {
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

    public void overdue() {
        this.overdue = true;
    }
}
