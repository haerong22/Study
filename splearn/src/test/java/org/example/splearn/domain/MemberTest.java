package org.example.splearn.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MemberTest {

    @Test
    void createMember() {
        var member = new Member("bobby@email.com", "bobby", "secret");

        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
    }

}