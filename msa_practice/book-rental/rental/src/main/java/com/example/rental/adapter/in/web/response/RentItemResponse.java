package com.example.rental.adapter.in.web.response;

import com.example.rental.domain.model.RentalItem;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class RentItemResponse {

    private Long itemNo;
    private String itemTitle;
    private LocalDate rentDate;
    private boolean overdue;
    private LocalDate overdueDate;

    @Builder
    private RentItemResponse(Long itemNo, String itemTitle, LocalDate rentDate, boolean overdue, LocalDate overdueDate) {
        this.itemNo = itemNo;
        this.itemTitle = itemTitle;
        this.rentDate = rentDate;
        this.overdue = overdue;
        this.overdueDate = overdueDate;
    }

    public static RentItemResponse toResponse(RentalItem rentItem) {
        return RentItemResponse.builder()
                .itemNo(rentItem.getItem().getNo())
                .itemTitle(rentItem.getItem().getTitle())
                .rentDate(rentItem.getRentDate())
                .overdue(rentItem.isOverdue())
                .overdueDate(rentItem.getOverdueDate())
                .build();
    }
}
