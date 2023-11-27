package com.example.member.adapter.out.jpa;

import com.example.member.adapter.out.jpa.entity.MemberJpaEntity;
import com.example.member.adapter.out.jpa.repository.MemberJpaRepository;
import com.example.member.domain.Member;
import com.example.member.domain.vo.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Import(MemberJpaAdapter.class)
@DataJpaTest
class MemberJpaAdapterTest {

    @Autowired
    private MemberJpaAdapter memberJpaAdapter;

    @Autowired
    private MemberJpaRepository memberJpaRepository;

    @Test
    @DisplayName("회원을 조회 할 수 있다.")
    void getMember() {
        // given
        MemberJpaEntity entity = createTestMemberJpaEntity();
        MemberJpaEntity saved = memberJpaRepository.save(entity);

        // when
        Member result = memberJpaAdapter.getMember(saved.getMemberNo());

        // then
        assertThat(result).isNotNull()
                .extracting(
                        "idName.id", "idName.name", "password.present",
                        "password.past", "email.address", "point.pointValue"
                )
                .containsExactly( "id", "name", "presentPwd", "pastPwd", "test@test.com", 0L);

        assertThat(result.getAuthorites()).hasSize(1);
        assertThat(result.getAuthorites().get(0).getRoleName()).isEqualTo(UserRole.USER);
    }

    @Test
    @DisplayName("회원이 없을 때는 null 을 리턴한다.")
    void getMemberNotExist() {
        // given

        // when
        Member result = memberJpaAdapter.getMember(1);

        // then
        assertThat(result).isNull();
    }

    @Test
    @DisplayName("IdName으로 회원을 조회 할 수 있다.")
    void getMemberByIdAndName() {
        // given
        IDName idName = IDName.create("id", "name");
        MemberJpaEntity entity = createTestMemberJpaEntity();
        memberJpaRepository.save(entity);

        // when
        Member result = memberJpaAdapter.getMemberByIdName(idName);

        // then
        assertThat(result).isNotNull()
                .extracting(
                        "idName.id", "idName.name", "password.present",
                        "password.past", "email.address", "point.pointValue"
                )
                .containsExactly("id", "name", "presentPwd", "pastPwd", "test@test.com", 0L);

        assertThat(result.getAuthorites()).hasSize(1);
        assertThat(result.getAuthorites().get(0).getRoleName()).isEqualTo(UserRole.USER);
    }

    @Test
    @DisplayName("IdName으로 회원을 조회 할 때 회원이 없으면 null 을 리턴한다.")
    void getMemberByIdAndNameNotExist() {
        // given
        IDName idName = IDName.create("id", "name");

        // when
        Member result = memberJpaAdapter.getMemberByIdName(idName);

        // then
        assertThat(result).isNull();
    }

    @Test
    @DisplayName("회원을 등록 할 수 있다.")
    void save() {
        // given
        Member member = createTestMember();

        // when
        Member result = memberJpaAdapter.save(member);

        // then
        assertThat(result).isNotNull()
                .extracting(
                        "idName.id", "idName.name", "password.present",
                        "password.past", "email.address", "point.pointValue"
                )
                .containsExactly("id", "name", "presentPwd", "pastPwd", "test@test.com", 0L);

        assertThat(result.getAuthorites()).hasSize(1);
        assertThat(result.getAuthorites().get(0).getRoleName()).isEqualTo(UserRole.USER);
    }

    private MemberJpaEntity createTestMemberJpaEntity() {
        return MemberJpaEntity.builder()
                .id("id")
                .name("name")
                .presentPwd("presentPwd")
                .pastPwd("pastPwd")
                .emailAddress("test@test.com")
                .point(0)
                .authorites(List.of(UserRole.USER))
                .build();
    }

    private Member createTestMember() {
        return Member.builder()
                .idName(IDName.create("id", "name"))
                .password(Password.create("presentPwd", "pastPwd"))
                .email(Email.create("test@test.com"))
                .authorites(new ArrayList<>() {{
                    add(Authority.create(UserRole.USER));
                }})
                .point(Point.create(0))
                .build();
    }
}