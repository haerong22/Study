package com.example.rental.adapter.in.web.response;

import com.example.rental.domain.model.RentalCard;
import com.example.rental.domain.model.RentalItem;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RentalCardResponse {

    private final String rentalCardId;
    private final String memberId;
    private final String memberName;
    private final String rentStatus;
    private final Long totalLateFee;
    private final Long totalRentalCnt;
    private final Long totalReturnCnt;
    private final Long totalOverduedCnt;

    @Builder
    private RentalCardResponse(String rentalCardId, String memberId, String memberName, String rentStatus, Long totalLateFee, Long totalRentalCnt, Long totalReturnCnt, Long totalOverduedCnt) {
        this.rentalCardId = rentalCardId;
        this.memberId = memberId;
        this.memberName = memberName;
        this.rentStatus = rentStatus;
        this.totalLateFee = totalLateFee;
        this.totalRentalCnt = totalRentalCnt;
        this.totalReturnCnt = totalReturnCnt;
        this.totalOverduedCnt = totalOverduedCnt;
    }

    public static RentalCardResponse toResponse(RentalCard rental){
        return RentalCardResponse.builder()
                .rentalCardId(rental.getRentalCardNo().getNo())
                .memberId(rental.getMember().getId())
                .memberName(rental.getMember().getName())
                .rentStatus(rental.getRentStatus().toString())
                .totalLateFee(rental.getLateFee().getPoint())
                .totalRentalCnt((long) rental.getRentalItems().size())
                .totalReturnCnt((long) rental.getReturnItems().size())
                .totalOverduedCnt(rental.getRentalItems().stream().filter(RentalItem::isOverdue).count())
                .build();
    }
}