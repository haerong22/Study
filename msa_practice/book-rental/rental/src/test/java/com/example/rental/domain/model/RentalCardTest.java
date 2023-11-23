package com.example.rental.domain.model;

import com.example.rental.domain.model.vo.IDName;
import com.example.rental.domain.model.vo.Item;
import com.example.rental.domain.model.vo.LateFee;
import com.example.rental.domain.model.vo.RentStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static com.example.rental.domain.model.vo.RentStatus.RENT_AVAILABLE;
import static com.example.rental.domain.model.vo.RentStatus.RENT_UNAVAILABLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.groups.Tuple.tuple;

class RentalCardTest {

    @Test
    @DisplayName("RentalCard를 생성할 수 있다.")
    void createRentalCard() {
        // given

        // when
        RentalCard result = RentalCard.createRentalCard(new IDName("001", "bobby"));

        // then
        assertThat(result.getMember()).isNotNull()
                .extracting("id", "name")
                .containsExactly("001", "bobby");

        assertThat(result.getRentStatus()).isEqualTo(RENT_AVAILABLE);
        assertThat(result.getLateFee().getPoint()).isEqualTo(0);
        assertThat(result.getRentalItems()).isEmpty();
        assertThat(result.getReturnItems()).isEmpty();
    }

    @Test
    @DisplayName("도서를 대여할 수 있다.")
    void rentItem() {
        // given
        RentalCard rentalCard = testRentalCard(new IDName("001", "bobby"), RENT_AVAILABLE);
        Item item = testItem(1L, "bobby");
        LocalDate rentDate = LocalDate.of(2023, 11, 22);

        // when
        RentalCard result = rentalCard.rentItem(item, rentDate);

        // then
        assertThat(result.getRentalItems()).hasSize(1)
                .extracting("item", "rentDate", "overdue", "overdueDate")
                .containsExactly(
                        tuple(item, rentDate, false, rentDate.plusDays(14))
                );
    }

    @Test
    @DisplayName("대여 불가 상태일 때 도서 대여를 할 수 없다.")
    void rentItemWithStatus() {
        // given
        RentalCard rentalCard = testRentalCard(new IDName("001", "bobby"), RENT_UNAVAILABLE);
        Item item = testItem(1L, "bobby");
        LocalDate rentDate = LocalDate.of(2023, 11, 22);

        // when

        // then
        assertThatThrownBy(() -> rentalCard.rentItem(item, rentDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("대여 불가 상태입니다.");
    }

    @Test
    @DisplayName("도서 대여를 최대 5권 할 수 있다.")
    void rentItemLimit() {
        // given
        LocalDate rentDate = LocalDate.of(2023, 11, 22);
        RentalCard rentalCard = testRentalCard(new IDName("001", "bobby"), RENT_AVAILABLE);
        rentalCard.getRentalItems().add(testRentalItem(testItem(1L, "bobby"), rentDate));
        rentalCard.getRentalItems().add(testRentalItem(testItem(2L, "bobby"), rentDate));
        rentalCard.getRentalItems().add(testRentalItem(testItem(3L, "bobby"), rentDate));
        rentalCard.getRentalItems().add(testRentalItem(testItem(4L, "bobby"), rentDate));
        rentalCard.getRentalItems().add(testRentalItem(testItem(5L, "bobby"), rentDate));

        Item newItem = testItem(6L, "bobby");

        // when

        // then
        assertThatThrownBy(() -> rentalCard.rentItem(newItem, rentDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 5권을 대여중입니다.");
    }

    @Test
    @DisplayName("도서를 반납할 수 있다.")
    void returnItem() {
        // given
        LocalDate rentDate = LocalDate.of(2023, 11, 22);
        LocalDate returnDate = LocalDate.of(2023, 11, 22);
        RentalCard rentalCard = testRentalCard(new IDName("001", "bobby"), RENT_AVAILABLE);
        Item item = testItem(1L, "bobby");
        rentalCard.getRentalItems().add(testRentalItem(item, rentDate));

        // when
        RentalCard result = rentalCard.returnItem(item, returnDate);

        // then
        assertThat(result.getRentalItems()).isEmpty();
        assertThat(result.getReturnItems()).hasSize(1);
    }

    @Test
    @DisplayName("대여한 도서가 없을 때 도서를 반납할 수 없다.")
    void returnItemWithNoItem() {
        // given
        LocalDate rentDate = LocalDate.of(2023, 11, 22);
        LocalDate returnDate = LocalDate.of(2023, 11, 22);
        RentalCard rentalCard = testRentalCard(new IDName("001", "bobby"), RENT_AVAILABLE);
        Item item = testItem(1L, "bobby");

        // when

        // then
        assertThatThrownBy(() -> rentalCard.returnItem(item, returnDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("반납할 도서가 없습니다.");
    }

    @Test
    @DisplayName("도서를 반납 시 연체료가 추가된다.(1일당 10포인트)")
    void returnItemLateFee() {
        // given
        LocalDate rentDate = LocalDate.of(2023, 1, 1);
        LocalDate returnDate = LocalDate.of(2023, 1, 20);
        RentalCard rentalCard = testRentalCard(new IDName("001", "bobby"), RENT_AVAILABLE);
        Item item = testItem(1L, "bobby");
        rentalCard.getRentalItems().add(testRentalItem(item, rentDate));

        // when
        RentalCard result = rentalCard.returnItem(item, returnDate);

        // then
        assertThat(result.getRentalItems()).isEmpty();
        assertThat(result.getReturnItems()).hasSize(1);
        assertThat(result.getLateFee().getPoint()).isEqualTo(5 * 10);
    }

    @Test
    @DisplayName("연체처리되면 대여 불가 상태로 변경된다.")
    void overdueItem() {
        // given
        LocalDate rentDate = LocalDate.of(2023, 1, 1);
        RentalCard rentalCard = testRentalCard(new IDName("001", "bobby"), RENT_AVAILABLE);
        Item item = testItem(1L, "bobby");
        rentalCard.getRentalItems().add(testRentalItem(item, rentDate));

        // when
        RentalCard result = rentalCard.overdueItem(item);

        // then
        assertThat(result.getRentalItems().get(0).isOverdue()).isTrue();
        assertThat(result.getRentStatus()).isEqualTo(RENT_UNAVAILABLE);
    }

    @Test
    @DisplayName("해당 도서가 없으면 연체처리 할 수 없다.")
    void overdueNoItem() {
        // given
        RentalCard rentalCard = testRentalCard(new IDName("001", "bobby"), RENT_AVAILABLE);
        Item item = testItem(1L, "bobby");

        // when

        // then
        assertThatThrownBy(() -> rentalCard.overdueItem(item))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 도서가 없습니다.");
    }

    @Test
    @DisplayName("대여 불가 상태를 해제할 수 있다.")
    void makeAvailableRental() {
        // given
        RentalCard rentalCard = testRentalCard(new IDName("001", "bobby"), RENT_UNAVAILABLE);

        // when
        rentalCard.makeAvailableRental(0);

        // then
        assertThat(rentalCard.getRentStatus()).isEqualTo(RENT_AVAILABLE);
    }

    @Test
    @DisplayName("반납되지 않은 도서가 있으면 대여 불가 상태를 해제할 수 없다.")
    void makeAvailableRentalFail() {
        // given
        LocalDate rentDate = LocalDate.of(2023, 1, 1);
        RentalCard rentalCard = testRentalCard(new IDName("001", "bobby"), RENT_UNAVAILABLE);
        Item item = testItem(1L, "bobby");
        rentalCard.getRentalItems().add(testRentalItem(item, rentDate));

        // when

        // then
        assertThatThrownBy(() -> rentalCard.makeAvailableRental(0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("모든 도서가 반납되어야 정지를 해제할 수 있습니다.");
    }

    @Test
    @DisplayName("대여 불가 상태를 해제할 때 연체료가 있으면 차감해야한다.")
    void makeAvailableRentalWithPoint() {
        // given
        RentalCard rentalCard = testRentalCard(new IDName("001", "bobby"), RENT_UNAVAILABLE, new LateFee(100));

        // when
        rentalCard.makeAvailableRental(100);

        // then
        assertThat(rentalCard.getLateFee().getPoint()).isEqualTo(0);
        assertThat(rentalCard.getRentStatus()).isEqualTo(RENT_AVAILABLE);
    }

    @Test
    @DisplayName("대여 불가 상태를 해제할 때 연체포인트가 다르면 해제할 수 없다.")
    void makeAvailableRentalWithNotEqualPoint() {
        // given
        RentalCard rentalCard = testRentalCard(new IDName("001", "bobby"), RENT_UNAVAILABLE, new LateFee(100));

        // when

        // then
        assertThatThrownBy(() -> rentalCard.makeAvailableRental(50))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 포인트로 연체를 해제할 수 없습니다.");
    }

    private RentalItem testRentalItem(Item item, LocalDate rentDate) {
        return RentalItem.builder()
                .item(item)
                .rentDate(rentDate)
                .overdue(false)
                .overdueDate(rentDate.plusDays(14))
                .build();
    }

    private RentalCard testRentalCard(IDName creator, RentStatus rentStatus) {
        return testRentalCard(creator, rentStatus, new LateFee(0));
    }

    private RentalCard testRentalCard(IDName creator, RentStatus rentStatus, LateFee lateFee) {
        return RentalCard.builder()
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

}