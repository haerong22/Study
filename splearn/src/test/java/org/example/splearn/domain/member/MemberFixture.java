package org.example.splearn.domain.member;

import org.springframework.test.util.ReflectionTestUtils;

public class MemberFixture {

    public static MemberRegisterRequest createMemberRegisterRequest(String email) {
        return new MemberRegisterRequest(email, "bobby", "longsecret");
    }

    public static MemberRegisterRequest createMemberRegisterRequest() {
        return createMemberRegisterRequest("bobby@email.com");
    }

    public static PasswordEncoder createPasswordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(String password) {
                return password.toUpperCase();
            }

            @Override
            public boolean matches(String password, String passwordHash) {
                return encode(password).equals(passwordHash);
            }
        };
    }

    public static Member createMember() {
        return Member.register(createMemberRegisterRequest(), createPasswordEncoder());
    }

    public static Member createMember(Long id) {
        Member member = Member.register(createMemberRegisterRequest(), createPasswordEncoder());
        ReflectionTestUtils.setField(member, "id", id);
        return member;
    }

    public static Member createMember(String email) {
        return Member.register(createMemberRegisterRequest(email), createPasswordEncoder());
    }
}
