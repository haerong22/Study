package org.example.splearn.application;

import lombok.RequiredArgsConstructor;
import org.example.splearn.application.provided.MemberRegister;
import org.example.splearn.application.required.EmailSender;
import org.example.splearn.application.required.MemberRepository;
import org.example.splearn.domain.Member;
import org.example.splearn.domain.MemberRegisterRequest;
import org.example.splearn.domain.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService implements MemberRegister {

    private final MemberRepository memberRepository;
    private final EmailSender emailSender;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Member register(MemberRegisterRequest registerRequest) {

        Member member = Member.register(registerRequest, passwordEncoder);

        memberRepository.save(member);

        emailSender.send(member.getEmail(), "등록을 완료해주세요", "아래 링크를 클릭해서 등록을 완료해주세요");

        return member;
    }
}
