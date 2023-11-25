package com.example.rental.application.sevice;

import com.example.rental.IntegrationTestSupport;
import com.example.rental.adapter.out.jpa.entity.RentalCardJpaEntity;
import com.example.rental.adapter.out.jpa.repository.RentalCardJpaRepository;
import com.example.rental.application.port.in.command.OverdueItemCommand;
import com.example.rental.domain.model.vo.RentStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.ArrayList;

import static com.example.rental.domain.model.vo.RentStatus.RENT_AVAILABLE;
import static com.example.rental.domain.model.vo.RentStatus.RENT_UNAVAILABLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OverdueItemServiceTest extends IntegrationTestSupport {
    
    @Autowired
    private OverdueItemService overdueItemService;
    
    @Autowired
    private RentalCardJpaRepository rentalCardJpaRepository;

    @Test
    @DisplayName("대여한 도서를 연체처리 할 수 있다.")
    void overdueItem() {
        // given
        RentalCardJpaEntity entity = testRentalCardJpaEntity(
                new RentalCardJpaEntity.RentalCardNo("TEST"),
                new RentalCardJpaEntity.IDName("001", "bobby"),
                RENT_AVAILABLE,
                new RentalCardJpaEntity.LateFee(0));

        LocalDate rentDate = LocalDate.of(2023, 1, 1);
        entity.getRentalItems().add(testJpaRentalItem(testJpaItem(1L, "SpringBoot"), rentDate));

        rentalCardJpaRepository.save(entity);

        OverdueItemCommand command = OverdueItemCommand.builder()
                .userId("001")
                .userNm("bobby")
                .itemId(1L)
                .itemTitle("SpringBoot")
                .build();

        // when
        overdueItemService.overdueItem(command);

        // then
        RentalCardJpaEntity result = rentalCardJpaRepository.findByMemberId("001").get();

        assertThat(result.getRentalItems().get(0).isOverdue()).isTrue();
        assertThat(result.getRentStatus()).isEqualTo(RENT_UNAVAILABLE);
    }

    @Test
    @DisplayName("대여카드가 없으면 연체처리 할 수 없다.")
    void overdueItemNoRentalCard() {
        // given
        OverdueItemCommand command = OverdueItemCommand.builder()
                .userId("001")
                .userNm("bobby")
                .itemId(1L)
                .itemTitle("SpringBoot")
                .build();

        // when

        // then
        assertThatThrownBy(() -> overdueItemService.overdueItem(command))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 카드가 없습니다.");
    }

    @Test
    @DisplayName("해당 도서가 없으면 연체처리 할 수 없다.")
    void overdueItemNoItem() {
        // given
        RentalCardJpaEntity entity = testRentalCardJpaEntity(
                new RentalCardJpaEntity.RentalCardNo("TEST"),
                new RentalCardJpaEntity.IDName("001", "bobby"),
                RENT_AVAILABLE,
                new RentalCardJpaEntity.LateFee(0));

        rentalCardJpaRepository.save(entity);

        OverdueItemCommand command = OverdueItemCommand.builder()
                .userId("001")
                .userNm("bobby")
                .itemId(1L)
                .itemTitle("SpringBoot")
                .build();

        // when

        // then
        assertThatThrownBy(() -> overdueItemService.overdueItem(command))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 도서가 없습니다.");
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

}