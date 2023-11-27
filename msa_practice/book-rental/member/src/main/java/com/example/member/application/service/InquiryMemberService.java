package com.example.member.application.service;

import com.example.member.application.port.in.InquiryMemberUseCase;
import com.example.member.application.port.out.MemberPort;
import com.example.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InquiryMemberService implements InquiryMemberUseCase {

    private final MemberPort memberPort;

    @Override
    public Member getMember(long memberNo) {
        return memberPort.getMember(memberNo);
    }
}
