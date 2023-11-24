package com.example.rental.adapter.in.web.response;

import com.example.rental.domain.model.vo.ReturnItem;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ReturnItemResponse {

    private Long itemNo;
    private String itemtitle;
    private LocalDate returnDate;

    @Builder
    private ReturnItemResponse(Long itemNo, String itemtitle, LocalDate returnDate) {
        this.itemNo = itemNo;
        this.itemtitle = itemtitle;
        this.returnDate = returnDate;
    }

    public static ReturnItemResponse toResponse(ReturnItem returnItem) {
        return ReturnItemResponse.builder()
                .itemNo(returnItem.getRentalItem().getItem().getNo())
                .itemtitle(returnItem.getRentalItem().getItem().getTitle())
                .returnDate(returnItem.getReturnDate())
                .build();
    }
}
