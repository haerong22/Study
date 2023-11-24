package com.example.rental.domain.model;

import com.example.rental.domain.model.vo.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Getter
public class RentalCard {

    private RentalCardNo rentalCardNo;
    private IDName member;
    private RentStatus rentStatus;
    private LateFee lateFee;
    private List<RentalItem> rentalItems;
    private List<ReturnItem> returnItems;

    public RentalCard() {
        this.rentalCardNo = new RentalCardNo("0");
        this.member = new IDName("0", "test");
        this.rentStatus = RentStatus.RENT_AVAILABLE;
        this.lateFee = new LateFee(0);
        this.rentalItems = new ArrayList<>();
        this.returnItems = new ArrayList<>();
    }

    @Builder
    private RentalCard(RentalCardNo rentalCardNo, IDName member, RentStatus rentStatus, LateFee lateFee, List<RentalItem> rentalItems, List<ReturnItem> returnItems) {
        this.rentalCardNo = rentalCardNo;
        this.member = member;
        this.rentStatus = rentStatus;
        this.lateFee = lateFee;
        this.rentalItems = rentalItems;
        this.returnItems = returnItems;
    }

    public static RentalCard createRentalCard(IDName creator) {
        return RentalCard.builder()
                .rentalCardNo(RentalCardNo.createRentalCardNo())
                .member(creator)
                .rentStatus(RentStatus.RENT_AVAILABLE)
                .lateFee(LateFee.createLateFee())
                .rentalItems(new ArrayList<>())
                .returnItems(new ArrayList<>())
                .build();
    }

    public RentalCard rentItem(Item item, LocalDate rentDate) {
        checkRentalAvailable();
        this.addRentalItem(RentalItem.createRentalItem(item, rentDate));
        return this;
    }

    public RentalCard returnItem(Item item, LocalDate returnDate) {
        RentalItem rentalItem = this.rentalItems.stream()
                .filter(i -> i.getItem().equals(item))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("반납할 도서가 없습니다."));

        calculateLateFee(rentalItem, returnDate);

        this.addReturnItem(ReturnItem.createReturnItem(rentalItem, returnDate));
        this.removeRentalItem(rentalItem);

        return this;
    }

    public RentalCard overdueItem(Item item) {
        RentalItem rentalItem = this.rentalItems.stream()
                .filter(i -> i.getItem().equals(item))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 도서가 없습니다."));

        rentalItem.overdue();
        this.rentStatus = RentStatus.RENT_UNAVAILABLE;

        return this;
    }

    public void makeAvailableRental(long point) {
        if (this.rentalItems.size() != 0) {
            throw new IllegalArgumentException("모든 도서가 반납되어야 정지를 해제할 수 있습니다.");
        }

        if (this.getLateFee().getPoint() != point) {
            throw new IllegalArgumentException("해당 포인트로 연체를 해제할 수 없습니다.");
        }

        this.lateFee = this.getLateFee().removePoint(point);
        this.rentStatus = RentStatus.RENT_AVAILABLE;
    }

    private void calculateLateFee(RentalItem rentalItem, LocalDate returnDate) {
        if (returnDate.compareTo(rentalItem.getOverdueDate()) > 0) {
            int point = Period.between(rentalItem.getOverdueDate(), returnDate).getDays() * 10;
            this.lateFee = this.lateFee.addPoint(point);
        }
    }

    private void checkRentalAvailable() {
        if (this.rentStatus == RentStatus.RENT_UNAVAILABLE) {
            throw new IllegalArgumentException("대여 불가 상태입니다.");
        }

        if (this.rentalItems.size() >= 5) {
            throw new IllegalArgumentException("이미 5권을 대여중입니다.");
        }
    }

    private void addRentalItem(RentalItem rentalItem) {
        this.rentalItems.add(rentalItem);
    }

    private void removeRentalItem(RentalItem rentalItem) {
        this.rentalItems.remove(rentalItem);
    }

    private void addReturnItem(ReturnItem returnItem) {
        this.returnItems.add(returnItem);
    }

    private void removeReturnItem(ReturnItem returnItem) {
        this.returnItems.remove(returnItem);
    }
}
