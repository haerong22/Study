package com.example.member.application.port.in;

import com.example.member.domain.Member;

public interface InquiryMemberUseCase {

    Member getMember(long memberNo);
}
