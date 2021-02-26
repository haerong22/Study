package com.example.apptest.member;

import com.example.apptest.domain.Member;

import java.util.Optional;

public interface MemberService {

    Optional<Member> findById(Long memberId);
}
