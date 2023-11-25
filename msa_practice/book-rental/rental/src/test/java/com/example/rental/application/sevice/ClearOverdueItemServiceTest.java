package com.example.rental.application.sevice;

import com.example.rental.IntegrationTestSupport;
import com.example.rental.adapter.out.jpa.entity.RentalCardJpaEntity;
import com.example.rental.adapter.out.jpa.repository.RentalCardJpaRepository;
import com.example.rental.application.port.in.command.ClearOverdueItemCommand;
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

class ClearOverdueItemServiceTest extends IntegrationTestSupport {

    @Autowired
    private ClearOverdueItemService clearOverdueItemService;

    @Autowired
    private RentalCardJpaRepository rentalCardJpaRepository;

    @Test
    @DisplayName("연체를 해제할 수 있다.")
    void clearOverdue() {
        // given
        RentalCardJpaEntity entity = testRentalCardJpaEntity(
                new RentalCardJpaEntity.RentalCardNo("TEST"),
                new RentalCardJpaEntity.IDName("001", "bobby"),
                RENT_UNAVAILABLE,
                new RentalCardJpaEntity.LateFee(100));

        rentalCardJpaRepository.save(entity);

        ClearOverdueItemCommand command = ClearOverdueItemCommand.builder()
                .userId("001")
                .userNm("bobby")
                .point(100)
                .build();

        // when
        clearOverdueItemService.clearOverdue(command);

        // then
        RentalCardJpaEntity result = rentalCardJpaRepository.findByMemberId("001").get();

        assertThat(result.getLateFee().getPoint()).isEqualTo(0);
        assertThat(result.getRentStatus()).isEqualTo(RENT_AVAILABLE);
    }

    @Test
    @DisplayName("대여카드가 없으면 연체를 해제할 수 없다.")
    void clearOverdueNoRentalCard() {
        // given
        ClearOverdueItemCommand command = ClearOverdueItemCommand.builder()
                .userId("001")
                .userNm("bobby")
                .point(100)
                .build();

        // when

        // then
        assertThatThrownBy(() -> clearOverdueItemService.clearOverdue(command))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 카드가 없습니다.");
    }

    @Test
    @DisplayName("대여중인 도서가 있으면 연체를 해제할 수 없다.")
    void clearOverdueWithRentItem() {
        // given
        RentalCardJpaEntity entity = testRentalCardJpaEntity(
                new RentalCardJpaEntity.RentalCardNo("TEST"),
                new RentalCardJpaEntity.IDName("001", "bobby"),
                RENT_UNAVAILABLE,
                new RentalCardJpaEntity.LateFee(100));

        LocalDate rentDate = LocalDate.of(2023, 1, 1);
        entity.getRentalItems().add(testJpaRentalItem(testJpaItem(1L, "SpringBoot"), rentDate));

        rentalCardJpaRepository.save(entity);

        ClearOverdueItemCommand command = ClearOverdueItemCommand.builder()
                .userId("001")
                .userNm("bobby")
                .point(100)
                .build();

        // when

        // then
        assertThatThrownBy(() -> clearOverdueItemService.clearOverdue(command))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("모든 도서가 반납되어야 정지를 해제할 수 있습니다.");
    }

    @Test
    @DisplayName("요청한 포인트가 연체 포인트와 다르면 연체를 해제할 수 없다.")
    void clearOverduePointNotMatch() {
        // given
        RentalCardJpaEntity entity = testRentalCardJpaEntity(
                new RentalCardJpaEntity.RentalCardNo("TEST"),
                new RentalCardJpaEntity.IDName("001", "bobby"),
                RENT_UNAVAILABLE,
                new RentalCardJpaEntity.LateFee(100));

        rentalCardJpaRepository.save(entity);

        ClearOverdueItemCommand command = ClearOverdueItemCommand.builder()
                .userId("001")
                .userNm("bobby")
                .point(50)
                .build();

        // when

        // then
        assertThatThrownBy(() -> clearOverdueItemService.clearOverdue(command))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 포인트로 연체를 해제할 수 없습니다.");
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