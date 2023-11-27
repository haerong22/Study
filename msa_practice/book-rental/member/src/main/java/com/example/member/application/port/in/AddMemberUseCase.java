package com.example.member.application.port.in;

import com.example.member.application.port.in.command.AddMemberCommand;
import com.example.member.domain.Member;

public interface AddMemberUseCase {

    Member addMember(AddMemberCommand command);
}
