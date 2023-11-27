package com.example.member.application.service;

import com.example.member.application.port.in.AddMemberUseCase;
import com.example.member.application.port.in.command.AddMemberCommand;
import com.example.member.application.port.out.MemberPort;
import com.example.member.domain.Member;
import com.example.member.domain.vo.Email;
import com.example.member.domain.vo.IDName;
import com.example.member.domain.vo.Password;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AddMemberService implements AddMemberUseCase {

    private final MemberPort memberPort;

    @Override
    public Member addMember(AddMemberCommand command) {
        IDName idName = IDName.create(command.getId(), command.getName());
        Password password = Password.create(command.getPassword(), command.getPassword());
        Email email = Email.create(command.getEmail());

        return memberPort.save(Member.register(idName, password, email));
    }
}
