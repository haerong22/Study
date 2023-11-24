package com.example.rental.application.sevice;

import com.example.rental.IntegrationTestSupport;
import com.example.rental.adapter.out.jpa.entity.RentalCardJpaEntity;
import com.example.rental.adapter.out.jpa.repository.RentalCardJpaRepository;
import com.example.rental.application.port.in.command.InquiryCommand;
import com.example.rental.domain.model.RentalCard;
import com.example.rental.domain.model.RentalItem;
import com.example.rental.domain.model.vo.RentStatus;
import com.example.rental.domain.model.vo.ReturnItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.example.rental.domain.model.vo.RentStatus.RENT_AVAILABLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

class InquiryServiceTest extends IntegrationTestSupport {

    @Autowired
    private InquiryService inquiryService;

    @Autowired
    private RentalCardJpaRepository rentalCardJpaRepository;

    @Test
    @DisplayName("RentalCard를 조회한다.")
    void getRentalCard() {
        // given
        RentalCardJpaEntity entity = testRentalCardJpaEntity(
                new RentalCardJpaEntity.RentalCardNo("TEST"),
                new RentalCardJpaEntity.IDName("001", "bobby"),
                RENT_AVAILABLE,
                new RentalCardJpaEntity.LateFee(100));
        rentalCardJpaRepository.save(entity);

        InquiryCommand request = InquiryCommand.builder()
                .userId("001")
                .userNm("bobby")
                .build();

        // when
        RentalCard result = inquiryService.getRentalCard(request);

        // then
        assertThat(result.getRentalCardNo()).isNotNull()
                .extracting("no")
                .isEqualTo("TEST");
        assertThat(result.getMember()).isNotNull()
                .extracting("id", "name")
                .containsExactly("001", "bobby");
        assertThat(result.getRentStatus()).isEqualTo(RENT_AVAILABLE);
        assertThat(result.getLateFee()).isNotNull()
                .extracting("point")
                .isEqualTo(100L);
        assertThat(result.getRentalItems()).isEmpty();
        assertThat(result.getReturnItems()).isEmpty();
    }

    @Test
    @DisplayName("RentalCard가 없으면 null 응답한다.")
    void getRentalCardNull() {
        // given
        InquiryCommand request = InquiryCommand.builder()
                .userId("001")
                .userNm("bobby")
                .build();

        // when
        RentalCard result = inquiryService.getRentalCard(request);

        // then
        assertThat(result).isNull();
    }

    @Test
    @DisplayName("대여중인 전체 도서를 조회한다.")
    void getAllRentItem() {
        // given
        RentalCardJpaEntity entity = testRentalCardJpaEntity(
                new RentalCardJpaEntity.RentalCardNo("TEST"),
                new RentalCardJpaEntity.IDName("001", "bobby"),
                RENT_AVAILABLE,
                new RentalCardJpaEntity.LateFee(100));

        LocalDate rentDate = LocalDate.of(2023, 1, 1);
        entity.getRentalItems().add(testJpaRentalItem(testJpaItem(1L, "Springboot"), rentDate));
        entity.getRentalItems().add(testJpaRentalItem(testJpaItem(2L, "Java"), rentDate));
        entity.getRentalItems().add(testJpaRentalItem(testJpaItem(3L, "Mysql"), rentDate));

        rentalCardJpaRepository.save(entity);

        InquiryCommand bobby = InquiryCommand.builder()
                .userId("001")
                .userNm("bobby")
                .build();

        // when
        List<RentalItem> result = inquiryService.getAllRentItem(bobby);

        // then
        assertThat(result).hasSize(3)
                .extracting("item.no", "item.title", "rentDate", "overdue", "overdueDate")
                .containsExactlyInAnyOrder(
                        tuple(1L, "Springboot", rentDate, false, rentDate.plusDays(14)),
                        tuple(2L, "Java", rentDate, false, rentDate.plusDays(14)),
                        tuple(3L, "Mysql", rentDate, false, rentDate.plusDays(14))
                );
    }

    @Test
    @DisplayName("대여중인 전체 도서를 조회 시 대여 카드가 없으면 빈 리스트를 응답한다.")
    void getAllRentItemEmpty() {
        // given
        InquiryCommand bobby = InquiryCommand.builder()
                .userId("001")
                .userNm("bobby")
                .build();

        // when
        List<RentalItem> result = inquiryService.getAllRentItem(bobby);

        // then
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("반납완료한 전체 도서를 조회한다.")
    void getAllReturnItem() {
        // given
        RentalCardJpaEntity entity = testRentalCardJpaEntity(
                new RentalCardJpaEntity.RentalCardNo("TEST"),
                new RentalCardJpaEntity.IDName("001", "bobby"),
                RENT_AVAILABLE,
                new RentalCardJpaEntity.LateFee(100));

        LocalDate rentDate = LocalDate.of(2023, 1, 1);
        LocalDate returnDate = LocalDate.of(2023, 1, 10);
        entity.getReturnItems().add(testJpaReturnItem(testJpaRentalItem(testJpaItem(1L, "Springboot"), rentDate), returnDate));
        entity.getReturnItems().add(testJpaReturnItem(testJpaRentalItem(testJpaItem(2L, "Java"), rentDate), returnDate));
        entity.getReturnItems().add(testJpaReturnItem(testJpaRentalItem(testJpaItem(3L, "Mysql"), rentDate), returnDate));

        rentalCardJpaRepository.save(entity);

        InquiryCommand bobby = InquiryCommand.builder()
                .userId("001")
                .userNm("bobby")
                .build();

        // when
        List<ReturnItem> result = inquiryService.getAllReturnItem(bobby);

        // then
        assertThat(result).hasSize(3)
                .extracting("rentalItem.item.no", "rentalItem.item.title", "rentalItem.rentDate", "rentalItem.overdue", "rentalItem.overdueDate", "returnDate")
                .containsExactlyInAnyOrder(
                        tuple(1L, "Springboot", rentDate, false, rentDate.plusDays(14), returnDate),
                        tuple(2L, "Java", rentDate, false, rentDate.plusDays(14), returnDate),
                        tuple(3L, "Mysql", rentDate, false, rentDate.plusDays(14), returnDate)
                );
    }

    @Test
    @DisplayName("반납완료한 전체 도서를 조회 시 대여 카드가 없으면 빈 리스트를 응답한다.")
    void getAllReturnItemEmpty() {
        // given
        InquiryCommand bobby = InquiryCommand.builder()
                .userId("001")
                .userNm("bobby")
                .build();

        // when
        List<ReturnItem> result = inquiryService.getAllReturnItem(bobby);

        // then
        assertThat(result).isEmpty();
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