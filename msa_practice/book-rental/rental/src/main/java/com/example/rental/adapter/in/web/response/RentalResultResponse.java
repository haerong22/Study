package com.example.rental.adapter.in.web.response;

import com.example.rental.domain.model.RentalCard;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RentalResultResponse {

    private String userId;
    private String userNm;
    private Integer rentedCount;
    private long totalLateFee;

    @Builder
    private RentalResultResponse(String userId, String userNm, Integer rentedCount, long totalLateFee) {
        this.userId = userId;
        this.userNm = userNm;
        this.rentedCount = rentedCount;
        this.totalLateFee = totalLateFee;
    }

    public static RentalResultResponse toResponse(RentalCard rental){
        return RentalResultResponse.builder()
                .userId(rental.getMember().getId())
                .userNm(rental.getMember().getName())
                .rentedCount(rental.getRentalItems().size())
                .totalLateFee(rental.getLateFee().getPoint())
                .build();
    }
}
