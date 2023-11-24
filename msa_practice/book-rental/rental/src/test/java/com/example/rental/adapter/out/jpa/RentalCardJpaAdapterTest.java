package com.example.rental.adapter.out.jpa;

import com.example.rental.adapter.out.jpa.entity.RentalCardJpaEntity;
import com.example.rental.adapter.out.jpa.repository.RentalCardJpaRepository;
import com.example.rental.domain.model.RentalCard;
import com.example.rental.domain.model.RentalItem;
import com.example.rental.domain.model.vo.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static com.example.rental.domain.model.vo.RentStatus.RENT_AVAILABLE;
import static org.assertj.core.api.Assertions.assertThat;

@Import({RentalCardJpaAdapter.class})
@DataJpaTest
class RentalCardJpaAdapterTest {

    @Autowired
    private RentalCardJpaAdapter rentalCardJpaAdapter;

    @Autowired
    private RentalCardJpaRepository repository;

    @Test
    @DisplayName("RentalCard를 저장 할 수 있다.")
    void save() {
        // given
        RentalCard rentalCard = testRentalCard(new RentalCardNo("TEST"), new IDName("001", "bobby"), RENT_AVAILABLE, new LateFee(100));
        LocalDate rentDate = LocalDate.of(2023, 11, 24);
        rentalCard.getRentalItems().add(testRentalItem(testItem(1L, "Springboot"), rentDate));

        // when
        rentalCardJpaAdapter.save(rentalCard);

        // then
        RentalCardJpaEntity saved = repository.findByMemberId("001").get();

        assertThat(saved.getRentalCardNo()).isNotNull()
                .extracting("no")
                .isEqualTo("TEST");
        assertThat(saved.getMember()).isNotNull()
                .extracting("id", "name")
                .containsExactly("001", "bobby");
        assertThat(saved.getLateFee()).isNotNull()
                .extracting("point")
                .isEqualTo(100L);
        assertThat(saved.getRentStatus()).isEqualTo(RENT_AVAILABLE);
        assertThat(saved.getRentalItems()).hasSize(1);
        assertThat(saved.getReturnItems()).isEmpty();
    }

    @Test
    @DisplayName("RentalCard를 조회 시 없으면 Optional.empty()를 반환한다.")
    void findRentalCardEmpty() {
        // given

        // when
        Optional<RentalCard> result = rentalCardJpaAdapter.findRentalCard("001");

        // then
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("MemberId로 RentalCard를 조회 할 수 있다.")
    void findRentalCard() {
        // given
        RentalCard rentalCard = testRentalCard(new RentalCardNo("TEST"), new IDName("001", "bobby"), RENT_AVAILABLE, new LateFee(100));
        rentalCardJpaAdapter.save(rentalCard);

        // when
        RentalCard result = rentalCardJpaAdapter.findRentalCard("001").get();

        // then
        assertThat(result.getRentalCardNo()).isNotNull()
                .extracting("no")
                .isEqualTo("TEST");
        assertThat(result.getMember()).isNotNull()
                .extracting("id", "name")
                .containsExactly("001", "bobby");
        assertThat(result.getLateFee()).isNotNull()
                .extracting("point")
                .isEqualTo(100L);
        assertThat(result.getRentStatus()).isEqualTo(RENT_AVAILABLE);
        assertThat(result.getRentalItems()).hasSize(0);
        assertThat(result.getReturnItems()).isEmpty();
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

    private static Item testItem(Long no, String title) {
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
}