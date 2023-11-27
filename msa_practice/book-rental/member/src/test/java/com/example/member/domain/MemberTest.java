package com.example.member.domain;

import com.example.member.domain.vo.Email;
import com.example.member.domain.vo.IDName;
import com.example.member.domain.vo.Password;
import com.example.member.domain.vo.Point;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberTest {

    @Test
    @DisplayName("회원을 생성할 수 있다.")
    void create() {
        // given
        IDName idname = IDName.create("001", "bobby");
        Password password = Password.create("new", "past");
        Email email = Email.create("test@test.com");

        // when
        Member member = Member.register(idname, password, email);

        // then
        assertThat(member).isNotNull()
                .extracting(
                        "idName.id", "idName.name", "password.present",
                        "password.past", "email.address", "point.pointValue"
                )
                .containsExactly("001", "bobby", "new", "past", "test@test.com", 0L);

        assertThat(member.getAuthorites()).hasSize(1);
    }

    @Test
    @DisplayName("포인트를 적립 할 수 있다.")
    void savePoint() {
        // given
        Member member = Member.builder()
                .point(Point.create(0))
                .build();

        // when
        member.savePoint(10);

        // then
        assertThat(member.getPoint().getPointValue()).isEqualTo(10L);
    }

    @Test
    @DisplayName("포인트를 사용 할 수 있다.")
    void usePoint() {
        // given
        Member member = Member.builder()
                .point(Point.create(100))
                .build();

        // when
        member.usePoint(10);

        // then
        assertThat(member.getPoint().getPointValue()).isEqualTo(90L);
    }

    @Test
    @DisplayName("사용 요청한 포인트가 보유 포인트보다 적으면 사용 할 수 있다.")
    void usePointFail() {
        // given
        Member member = Member.builder()
                .point(Point.create(0))
                .build();

        // when

        // then
        assertThatThrownBy(() -> member.usePoint(10))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("기존 보유 포인트보다 적어 삭제할 수 없습니다.");
    }

}