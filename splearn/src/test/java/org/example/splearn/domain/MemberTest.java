package org.example.splearn.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberTest {

    @Test
    void createMember() {
        var member = new Member("bobby@email.com", "bobby", "secret");

        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
    }

    @Test
    void constructorNullCheck() {
        assertThatThrownBy(() ->
                new Member(null, "bobby", "secret")
        ).isInstanceOf(NullPointerException.class);
    }

    @Test
    void activate() {
        var member = new Member("bobby@email.com", "bobby", "secret");

        member.activate();

        assertThat(member.getStatus()).isEqualTo(MemberStatus.ACTIVE);
    }

    @Test
    void activateFail() {
        var member = new Member("bobby@email.com", "bobby", "secret");
        member.activate();

        assertThatThrownBy(() ->
                member.activate()
        )
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("PENDING 상태가 아닙니다.");
    }

    @Test
    void deactivate() {
        var member = new Member("bobby@email.com", "bobby", "secret");
        member.activate();

        member.deactivate();

        assertThat(member.getStatus()).isEqualTo(MemberStatus.DEACTIVATED);
    }

    @Test
    void deactivateFail() {
        var member = new Member("bobby@email.com", "bobby", "secret");
        assertThatThrownBy(() ->
                member.deactivate()
        )
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("ACTIVE 상태가 아닙니다.");
    }

    @Test
    void deactivateFail2() {
        var member = new Member("bobby@email.com", "bobby", "secret");
        member.activate();
        member.deactivate();

        assertThatThrownBy(() ->
                member.deactivate()
        )
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("ACTIVE 상태가 아닙니다.");
    }
}