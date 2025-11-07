package org.example.splearn.application.member.provided;

import jakarta.persistence.EntityManager;
import jakarta.validation.ConstraintViolationException;
import org.example.splearn.SplearnTestConfiguration;
import org.example.splearn.domain.member.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
@Import(SplearnTestConfiguration.class)
record MemberRegisterTest(
        MemberRegister memberRegister,
        EntityManager entityManager
) {

    @Test
    void register() {
        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());

        assertThat(member.getId()).isNotNull();
        assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
    }

    @Test
    void duplicateEmailFail() {
        memberRegister.register(MemberFixture.createMemberRegisterRequest());

        assertThatThrownBy(() -> memberRegister.register(MemberFixture.createMemberRegisterRequest()))
                .isInstanceOf(DuplicateEmailException.class);
    }

    @Test
    void activate() {
        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());
        entityManager.flush();
        entityManager.clear();

        member = memberRegister.activate(member.getId());
        entityManager.flush();

        assertThat(member.getStatus()).isEqualTo(MemberStatus.ACTIVE);
    }

    @Test
    void memberRegisterRequestFail() {
        checkValidation(new MemberRegisterRequest("bobby@email.com", "name", "longsecret"));
        checkValidation(new MemberRegisterRequest("bobby@email.com", "long_nickname_________________", "longsecret"));
        checkValidation(new MemberRegisterRequest("bobbyemail.com", "bobby", "longsecret"));
    }

    private void checkValidation(MemberRegisterRequest invalid) {
        assertThatThrownBy(() -> memberRegister.register(invalid))
                .isInstanceOf(ConstraintViolationException.class);
    }
}
