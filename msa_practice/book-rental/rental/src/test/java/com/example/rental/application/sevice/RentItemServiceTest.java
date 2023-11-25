package com.example.rental.application.sevice;

import com.example.rental.IntegrationTestSupport;
import com.example.rental.adapter.out.jpa.entity.RentalCardJpaEntity;
import com.example.rental.adapter.out.jpa.repository.RentalCardJpaRepository;
import com.example.rental.application.port.in.command.RentItemCommand;
import com.example.rental.domain.model.vo.RentStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.example.rental.domain.model.vo.RentStatus.RENT_AVAILABLE;
import static com.example.rental.domain.model.vo.RentStatus.RENT_UNAVAILABLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.groups.Tuple.tuple;

class RentItemServiceTest extends IntegrationTestSupport {

    @Autowired
    private RentItemService rentItemService;

    @Autowired
    private RentalCardJpaRepository rentalCardJpaRepository;

    @Test
    @DisplayName("도서를 대여할 수 있다.")
    void rentItem() {
        // given
        RentalCardJpaEntity entity = testRentalCardJpaEntity(
                new RentalCardJpaEntity.RentalCardNo("TEST"),
                new RentalCardJpaEntity.IDName("001", "bobby"),
                RENT_AVAILABLE,
                new RentalCardJpaEntity.LateFee(100));

        rentalCardJpaRepository.save(entity);

        RentItemCommand command = RentItemCommand.builder()
                .userId("001")
                .userNm("bobby")
                .itemId(1L)
                .itemTitle("SpringBoot")
                .build();

        // when
        rentItemService.rentItem(command);

        // then
        RentalCardJpaEntity result = rentalCardJpaRepository.findByMemberId("001").get();

        assertThat(result.getRentalItems()).hasSize(1)
                .extracting("item.no", "item.title")
                .containsExactlyInAnyOrder(
                        tuple(1L, "SpringBoot")
                );
    }

    @Test
    @DisplayName("도서를 대여할 때 대여카드가 없으면 생성 후 대여할 수 있다.")
    void rentItemNoRentalCard() {
        // given
        RentItemCommand command = RentItemCommand.builder()
                .userId("001")
                .userNm("bobby")
                .itemId(1L)
                .itemTitle("SpringBoot")
                .build();

        // when
        rentItemService.rentItem(command);

        // then
        RentalCardJpaEntity result = rentalCardJpaRepository.findByMemberId("001").get();

        assertThat(result.getRentalItems()).hasSize(1)
                .extracting("item.no", "item.title")
                .containsExactlyInAnyOrder(
                        tuple(1L, "SpringBoot")
                );
    }

    @Test
    @DisplayName("대여 불가 상태일 경우 도서를 대여할 수 없다.")
    void rentItemUnavailableStatus() {
        // given
        RentalCardJpaEntity entity = testRentalCardJpaEntity(
                new RentalCardJpaEntity.RentalCardNo("TEST"),
                new RentalCardJpaEntity.IDName("001", "bobby"),
                RENT_UNAVAILABLE,
                new RentalCardJpaEntity.LateFee(100));

        rentalCardJpaRepository.save(entity);

        RentItemCommand command = RentItemCommand.builder()
                .userId("001")
                .userNm("bobby")
                .itemId(1L)
                .itemTitle("SpringBoot")
                .build();

        // when

        // then
        assertThatThrownBy(() -> rentItemService.rentItem(command))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("대여 불가 상태입니다.");
    }

    @Test
    @DisplayName("이미 대여중인 도서가 5권 이상 있으면 도서를 대여할 수 없다.")
    void rentItemLimit() {
        // given
        RentalCardJpaEntity entity = testRentalCardJpaEntity(
                new RentalCardJpaEntity.RentalCardNo("TEST"),
                new RentalCardJpaEntity.IDName("001", "bobby"),
                RENT_AVAILABLE,
                new RentalCardJpaEntity.LateFee(100));

        entity.getRentalItems().addAll(List.of(testJpaRentalItem(), testJpaRentalItem(), testJpaRentalItem(), testJpaRentalItem(), testJpaRentalItem()));

        rentalCardJpaRepository.save(entity);

        RentItemCommand command = RentItemCommand.builder()
                .userId("001")
                .userNm("bobby")
                .itemId(1L)
                .itemTitle("SpringBoot")
                .build();

        // when

        // then
        assertThatThrownBy(() -> rentItemService.rentItem(command))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 5권을 대여중입니다.");
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

    private RentalCardJpaEntity.RentalItem testJpaRentalItem() {
        LocalDate now = LocalDate.now();
        return RentalCardJpaEntity.RentalItem.builder()
                .item(RentalCardJpaEntity.Item.builder().build())
                .rentDate(now)
                .overdue(false)
                .overdueDate(now.plusDays(14))
                .build();
    }
}