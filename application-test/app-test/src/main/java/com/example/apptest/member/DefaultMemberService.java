package com.example.apptest.member;

import com.example.apptest.domain.Member;
import com.example.apptest.domain.Study;
import com.example.apptest.study.StudyService;

import java.util.Optional;

public class DefaultMemberService implements MemberService{

    StudyService studyService;

    @Override
    public Optional<Member> findById(Long memberId) {
        return Optional.empty();
    }

    @Override
    public void validate(Long memberId) {
        studyService.openStudy(new Study());
    }

    @Override
    public void notify(Study newStudy) {

    }

    @Override
    public void notify(Member member) {

    }
}
