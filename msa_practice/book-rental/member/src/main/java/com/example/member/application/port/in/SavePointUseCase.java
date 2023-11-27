package com.example.member.application.port.in;

import com.example.member.domain.Member;
import com.example.member.domain.vo.IDName;

public interface SavePointUseCase {

    Member savePoint(IDName idName, long point);
}
