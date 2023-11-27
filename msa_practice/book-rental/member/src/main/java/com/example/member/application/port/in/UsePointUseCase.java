package com.example.member.application.port.in;

import com.example.member.domain.Member;
import com.example.member.domain.vo.IDName;

public interface UsePointUseCase {

    Member usePoint(IDName idName, long point);
}
