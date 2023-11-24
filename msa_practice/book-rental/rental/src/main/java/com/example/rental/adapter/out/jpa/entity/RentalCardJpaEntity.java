package com.example.rental.adapter.out.jpa.entity;

import com.example.rental.domain.model.RentalCard;
import com.example.rental.domain.model.vo.RentStatus;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Table(name = "rental_card")
@Entity
@NoArgsConstructor
public class RentalCardJpaEntity {

    @EmbeddedId
    private RentalCardNo rentalCardNo;

    @Embedded
    private IDName member;

    @Enumerated(EnumType.STRING)
    private RentStatus rentStatus;

    @Embedded
    private LateFee lateFee;

    @ElementCollection
    @CollectionTable(name = "rental_item", joinColumns = @JoinColumn(name = "rental_card_no"))
    private List<RentalItem> rentalItems = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "return_item", joinColumns = @JoinColumn(name = "rental_card_no"))
    private List<ReturnItem> returnItems = new ArrayList<>();

    @Builder
    private RentalCardJpaEntity(RentalCardNo rentalCardNo, IDName member, RentStatus rentStatus, LateFee lateFee, List<RentalItem> rentalItems, List<ReturnItem> returnItems) {
        this.rentalCardNo = rentalCardNo;
        this.member = member;
        this.rentStatus = rentStatus;
        this.lateFee = lateFee;
        this.rentalItems = rentalItems;
        this.returnItems = returnItems;
    }

    public static RentalCardJpaEntity fromDomain(RentalCard rentalCard) {
        return RentalCardJpaEntity.builder()
                .rentalCardNo(new RentalCardNo(rentalCard.getRentalCardNo().getNo()))
                .member(new IDName(rentalCard.getMember().getId(), rentalCard.getMember().getName()))
                .rentStatus(rentalCard.getRentStatus())
                .lateFee(new LateFee(rentalCard.getLateFee().getPoint()))
                .rentalItems(
                        rentalCard.getRentalItems().stream()
                                .map(i -> new RentalItem(new Item(i.getItem().getNo(), i.getItem().getTitle()), i.getRentDate(), i.isOverdue(), i.getOverdueDate()))
                                .collect(Collectors.toList())
                )
                .returnItems(
                        rentalCard.getReturnItems().stream()
                                .map(i -> {
                                    RentalItem rentalItem = new RentalItem(new Item(i.getRentalItem().getItem().getNo(), i.getRentalItem().getItem().getTitle()), i.getRentalItem().getRentDate(), i.getRentalItem().isOverdue(), i.getRentalItem().getOverdueDate());
                                    return new ReturnItem(rentalItem, i.getReturnDate());
                                })
                                .collect(Collectors.toList())
                )
                .build();
    }

    public RentalCard toDomain() {
        return RentalCard.builder()
                .rentalCardNo(new com.example.rental.domain.model.vo.RentalCardNo(rentalCardNo.no))
                .member(new com.example.rental.domain.model.vo.IDName(member.id, member.name))
                .rentStatus(rentStatus)
                .lateFee(new com.example.rental.domain.model.vo.LateFee(lateFee.point))
                .rentalItems(
                        rentalItems.stream()
                                .map(i -> new com.example.rental.domain.model.RentalItem(
                                        com.example.rental.domain.model.vo.Item.create(i.item.no, i.item.title),
                                        i.rentDate,
                                        i.overdue,
                                        i.overdueDate
                                ))
                                .collect(Collectors.toList())
                )
                .returnItems(
                        returnItems.stream()
                                .map(i -> new com.example.rental.domain.model.vo.ReturnItem(
                                        new com.example.rental.domain.model.RentalItem(
                                                com.example.rental.domain.model.vo.Item.create(i.rentalItem.item.no, i.rentalItem.item.title),
                                                i.rentalItem.rentDate,
                                                i.rentalItem.overdue,
                                                i.rentalItem.overdueDate
                                        ),
                                        i.returnDate
                                ))
                                .collect(Collectors.toList())
                )
                .build();
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Embeddable
    public static class RentalCardNo implements Serializable {

        private String no;
    }

    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Embeddable
    public static class IDName {

        private String id;
        private String name;
    }

    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Embeddable
    public static class LateFee {

        private long point;
    }

    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Embeddable
    @Builder
    public static class RentalItem {

        @Embedded
        private Item item;
        private LocalDate rentDate;
        private boolean overdue;
        private LocalDate overdueDate;
    }

    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Embeddable
    @Builder
    public static class Item {

        private Long no;
        private String title;
    }

    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Embeddable
    @Builder
    public static class ReturnItem {

        @Embedded
        private RentalItem rentalItem;
        private LocalDate returnDate;
    }

}
