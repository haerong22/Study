package org.example.splearn.domain;

public record MemberCreateRequest(
        String email,
        String nickname,
        String password
) {
}
