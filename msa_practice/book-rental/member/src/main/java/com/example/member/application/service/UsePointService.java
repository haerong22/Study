package com.example.member.application.service;

import com.example.member.application.port.in.UsePointUseCase;
import com.example.member.application.port.out.MemberPort;
import com.example.member.domain.Member;
import com.example.member.domain.vo.IDName;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UsePointService implements UsePointUseCase {

    private final MemberPort memberPort;

    @Override
    public Member usePoint(IDName idName, long point) {
        Member member = memberPort.getMemberByIdName(idName);
        member.usePoint(point);
        return memberPort.save(member);
    }
}
