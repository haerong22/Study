package com.example.rental.adapter.out.jpa.entity;

import com.example.rental.domain.model.RentalCard;
import com.example.rental.domain.model.RentalItem;
import com.example.rental.domain.model.vo.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static com.example.rental.domain.model.vo.RentStatus.RENT_AVAILABLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class RentalCardJpaEntityTest {

    @Test
    @DisplayName("도메인 엔티티를 JPA엔티티로 매핑 할 수 있다.")
    void fromDomain() {
        // given
        RentalCard rentalCard = testRentalCard(new RentalCardNo("TEST"), new IDName("001", "bobby"), RENT_AVAILABLE, new LateFee(100));
        LocalDate rentDate = LocalDate.of(2023, 1, 1);
        LocalDate returnDate = LocalDate.of(2023, 1, 10);
        RentalItem rentalItem = testRentalItem(testItem(1L, "Springboot"), rentDate);
        rentalCard.getRentalItems().add(rentalItem);
        rentalCard.getReturnItems().add(testReturnItem(rentalItem, returnDate));

        // when
        RentalCardJpaEntity entity = RentalCardJpaEntity.fromDomain(rentalCard);

        // then
        assertThat(entity.getRentalCardNo()).isNotNull()
                .extracting("no")
                .isEqualTo("TEST");
        assertThat(entity.getMember()).isNotNull()
                .extracting("id", "name")
                .containsExactly("001", "bobby");
        assertThat(entity.getRentStatus()).isEqualTo(RENT_AVAILABLE);
        assertThat(entity.getLateFee()).isNotNull()
                .extracting("point")
                .isEqualTo(100L);
        assertThat(entity.getRentalItems()).hasSize(1)
                .extracting("item.no", "item.title", "rentDate", "overdue", "overdueDate")
                .containsExactlyInAnyOrder(
                        tuple(1L, "Springboot", rentDate, false, rentDate.plusDays(14))
                );
        assertThat(entity.getReturnItems()).hasSize(1)
                .extracting("rentalItem.item.no", "rentalItem.item.title", "rentalItem.rentDate", "rentalItem.overdue", "rentalItem.overdueDate", "returnDate")
                .containsExactlyInAnyOrder(
                        tuple(1L, "Springboot", rentDate, false, rentDate.plusDays(14), returnDate)
                );
    }

    @Test
    @DisplayName("JPA엔티티를 도메인 엔티티로 매핑 할 수 있다.")
    void toDomain() {
        // given
        RentalCardJpaEntity entity = testRentalCardJpaEntity(
                new RentalCardJpaEntity.RentalCardNo("TEST"),
                new RentalCardJpaEntity.IDName("001", "bobby"),
                RENT_AVAILABLE,
                new RentalCardJpaEntity.LateFee(100));
        LocalDate rentDate = LocalDate.of(2023, 1, 1);
        LocalDate returnDate = LocalDate.of(2023, 1, 10);
        RentalCardJpaEntity.RentalItem rentalItem = testJpaRentalItem(testJpaItem(1L, "Springboot"), rentDate);
        entity.getRentalItems().add(rentalItem);
        entity.getReturnItems().add(testJpaReturnItem(rentalItem, returnDate));

        // when
        RentalCard domain = entity.toDomain();

        // then
        assertThat(domain.getRentalCardNo()).isNotNull()
                .extracting("no")
                .isEqualTo("TEST");
        assertThat(domain.getMember()).isNotNull()
                .extracting("id", "name")
                .containsExactly("001", "bobby");
        assertThat(domain.getRentStatus()).isEqualTo(RENT_AVAILABLE);
        assertThat(domain.getLateFee()).isNotNull()
                .extracting("point")
                .isEqualTo(100L);
        assertThat(domain.getRentalItems()).hasSize(1)
                .extracting("item.no", "item.title", "rentDate", "overdue", "overdueDate")
                .containsExactlyInAnyOrder(
                        tuple(1L, "Springboot", rentDate, false, rentDate.plusDays(14))
                );
        assertThat(domain.getReturnItems()).hasSize(1)
                .extracting("rentalItem.item.no", "rentalItem.item.title", "rentalItem.rentDate", "rentalItem.overdue", "rentalItem.overdueDate", "returnDate")
                .containsExactlyInAnyOrder(
                        tuple(1L, "Springboot", rentDate, false, rentDate.plusDays(14), returnDate)
                );
    }

    private RentalCard testRentalCard(RentalCardNo rentalCardNo, IDName creator, RentStatus rentStatus, LateFee lateFee) {
        return RentalCard.builder()
                .rentalCardNo(rentalCardNo)
                .member(creator)
                .lateFee(lateFee)
                .rentStatus(rentStatus)
                .rentalItems(new ArrayList<>())
                .returnItems(new ArrayList<>())
                .build();
    }

    private Item testItem(Long no, String title) {
        return Item.builder()
                .no(no)
                .title(title)
                .build();
    }

    private RentalItem testRentalItem(Item item, LocalDate rentDate) {
        return RentalItem.builder()
                .item(item)
                .rentDate(rentDate)
                .overdue(false)
                .overdueDate(rentDate.plusDays(14))
                .build();
    }

    private ReturnItem testReturnItem(RentalItem rentalItem, LocalDate returnDate) {
        return ReturnItem.builder()
                .rentalItem(rentalItem)
                .returnDate(returnDate)
                .build();
    }

    private RentalCardJpaEntity testRentalCardJpaEntity(
            RentalCardJpaEntity.RentalCardNo rentalCardNo,
            RentalCardJpaEntity.IDName creator,
            RentStatus rentStatus,
            RentalCardJpaEntity.LateFee lateFee
    ) {
        return RentalCardJpaEntity.builder()
                .rentalCardNo(rentalCardNo)
                .member(creator)
                .lateFee(lateFee)
                .rentStatus(rentStatus)
                .rentalItems(new ArrayList<>())
                .returnItems(new ArrayList<>())
                .build();
    }

    private RentalCardJpaEntity.Item testJpaItem(Long no, String title) {
        return RentalCardJpaEntity.Item.builder()
                .no(no)
                .title(title)
                .build();
    }

    private RentalCardJpaEntity.RentalItem testJpaRentalItem (
            RentalCardJpaEntity.Item item,
            LocalDate rentDate
    ) {
        return RentalCardJpaEntity.RentalItem.builder()
                .item(item)
                .rentDate(rentDate)
                .overdue(false)
                .overdueDate(rentDate.plusDays(14))
                .build();
    }

    private RentalCardJpaEntity.ReturnItem testJpaReturnItem(
            RentalCardJpaEntity.RentalItem rentalItem,
            LocalDate returnDate
    ) {
        return RentalCardJpaEntity.ReturnItem.builder()
                .rentalItem(rentalItem)
                .returnDate(returnDate)
                .build();
    }

}