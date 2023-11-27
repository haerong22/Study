package com.example.member.application.port.out;

import com.example.member.domain.Member;
import com.example.member.domain.vo.IDName;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberPort {

    Member getMember(long memberNo);

    Member getMemberByIdName(IDName idName);

    Member save(Member member);
}
