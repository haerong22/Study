package com.example.rental.application.sevice;

import com.example.rental.IntegrationTestSupport;
import com.example.rental.adapter.out.jpa.entity.RentalCardJpaEntity;
import com.example.rental.adapter.out.jpa.repository.RentalCardJpaRepository;
import com.example.rental.application.port.in.command.CreateRentalCardCommand;
import com.example.rental.domain.model.RentalCard;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class CreateRentalCardServiceTest extends IntegrationTestSupport {

    @Autowired
    private CreateRentalCardService createRentalCardService;

    @Autowired
    private RentalCardJpaRepository rentalCardJpaRepository;

    @Test
    @DisplayName("RentalCard를 생성 할 수 있다.")
    void createRentalCard() {
        // given
        CreateRentalCardCommand command = CreateRentalCardCommand.builder()
                .userId("001")
                .userNm("bobby")
                .build();

        // when
        RentalCard result = createRentalCardService.createRentalCard(command);

        // then
        RentalCardJpaEntity entity = rentalCardJpaRepository.findByMemberId("001").get();

        assertThat(result.getRentalCardNo().getNo()).isEqualTo(entity.getRentalCardNo().getNo());
        assertThat(result.getMember().getId()).isEqualTo("001");
        assertThat(result.getMember().getName()).isEqualTo("bobby");
    }
}