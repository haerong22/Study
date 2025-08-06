package org.example.splearn.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.example.splearn.domain.MemberFixture.createMemberRegisterRequest;
import static org.example.splearn.domain.MemberFixture.createPasswordEncoder;

class MemberTest {

    Member member;
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        this.passwordEncoder = createPasswordEncoder();

        this.member = Member.register(createMemberRegisterRequest(), passwordEncoder);
    }

    @Test
    void registerMember() {
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
    }

    @Test
    void activate() {
        member.activate();

        assertThat(member.getStatus()).isEqualTo(MemberStatus.ACTIVE);
    }

    @Test
    void activateFail() {
        member.activate();

        assertThatThrownBy(() ->
                member.activate()
        )
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("PENDING 상태가 아닙니다.");
    }

    @Test
    void deactivate() {
        member.activate();

        member.deactivate();

        assertThat(member.getStatus()).isEqualTo(MemberStatus.DEACTIVATED);
    }

    @Test
    void deactivateFail() {
        assertThatThrownBy(() ->
                member.deactivate()
        )
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("ACTIVE 상태가 아닙니다.");
    }

    @Test
    void deactivateFail2() {
        member.activate();
        member.deactivate();

        assertThatThrownBy(() ->
                member.deactivate()
        )
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("ACTIVE 상태가 아닙니다.");
    }

    @Test
    void verifyPassword() {
        assertThat(member.verifyPassword("longsecret", passwordEncoder)).isTrue();
        assertThat(member.verifyPassword("hello", passwordEncoder)).isFalse();
    }

    @Test
    void changeNickname() {
        assertThat(member.getNickname()).isEqualTo("bobby");

        member.changeNickname("bob");

        assertThat(member.getNickname()).isEqualTo("bob");
    }

    @Test
    void changePassword() {
        member.changePassword("verysecret", passwordEncoder);

        assertThat(member.verifyPassword("verysecret", passwordEncoder)).isTrue();
    }

    @Test
    void isActive() {
        assertThat(member.isActive()).isFalse();

        member.activate();

        assertThat(member.isActive()).isTrue();

        member.deactivate();

        assertThat(member.isActive()).isFalse();
    }

    @Test
    void invalidEmail() {
        assertThatThrownBy(() ->
                Member.register(createMemberRegisterRequest("invalid email"), passwordEncoder)
        ).isInstanceOf(IllegalArgumentException.class);

        Member.register(createMemberRegisterRequest(), passwordEncoder);
    }
}