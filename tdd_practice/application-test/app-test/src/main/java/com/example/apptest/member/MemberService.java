package com.example.apptest.member;

import com.example.apptest.domain.Member;
import com.example.apptest.domain.Study;

import java.util.Optional;

public interface MemberService {

    Optional<Member> findById(Long memberId);

    void validate(Long memberId);

    void notify(Study newStudy);

    void notify(Member member);
}
