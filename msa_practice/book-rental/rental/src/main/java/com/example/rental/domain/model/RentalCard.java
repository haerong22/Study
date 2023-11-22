package com.example.rental.domain.model;

import com.example.rental.domain.model.vo.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class RentalCard {

    private RentalCardNo rentalCardNo;
    private IDName member;
    private RentStatus rentStatus;
    private LateFee lateFee;
    private List<RentalItem> rentalItems = new ArrayList<>();
    private List<ReturnItem> returnItems = new ArrayList<>();

    @Builder
    private RentalCard(RentalCardNo rentalCardNo, IDName member, RentStatus rentStatus, LateFee lateFee, List<RentalItem> rentalItems, List<ReturnItem> returnItems) {
        this.rentalCardNo = rentalCardNo;
        this.member = member;
        this.rentStatus = rentStatus;
        this.lateFee = lateFee;
        this.rentalItems = rentalItems;
        this.returnItems = returnItems;
    }

    public void addRentalItem(RentalItem rentalItem) {
        this.rentalItems.add(rentalItem);
    }

    public void removeRentalItem(RentalItem rentalItem) {
        this.rentalItems.remove(rentalItem);
    }

    public void addReturnItem(ReturnItem returnItem) {
        this.returnItems.add(returnItem);
    }

    public void removeReturnItem(ReturnItem returnItem) {
        this.returnItems.remove(returnItem);
    }
}
