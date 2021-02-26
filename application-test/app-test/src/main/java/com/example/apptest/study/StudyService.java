package com.example.apptest.study;

import com.example.apptest.domain.Member;
import com.example.apptest.domain.Study;
import com.example.apptest.member.MemberService;

public class StudyService {

    private final MemberService memberService;
    private final StudyRepository studyRepository;

    public StudyService(MemberService memberService, StudyRepository studyRepository) {
        assert memberService != null;
        assert studyRepository != null;
        this.memberService = memberService;
        this.studyRepository = studyRepository;
    }

    public Study createNewStudy(Long memberId, Study study) {
        Member member = memberService.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member doesn't exist for id: " + memberId));
        study.setOwnerId(memberId);
        Study newStudy = studyRepository.save(study);
        memberService.notify(newStudy);
        memberService.notify(member);
        return newStudy;
    }
}
