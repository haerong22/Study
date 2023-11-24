package com.example.rental.application.sevice;

import com.example.rental.IntegrationTestSupport;
import com.example.rental.adapter.out.jpa.entity.RentalCardJpaEntity;
import com.example.rental.adapter.out.jpa.repository.RentalCardJpaRepository;
import com.example.rental.application.port.in.command.InquiryCommand;
import com.example.rental.domain.model.RentalCard;
import com.example.rental.domain.model.vo.RentStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

import static com.example.rental.domain.model.vo.RentStatus.RENT_AVAILABLE;
import static org.assertj.core.api.Assertions.assertThat;

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

        InquiryCommand bobby = InquiryCommand.builder()
                .userId("001")
                .userNm("bobby")
                .build();

        // when
        RentalCard result = inquiryService.getRentalCard(bobby);

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
}