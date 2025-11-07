package org.example.splearn.application.member.provided;

import org.example.splearn.domain.member.Member;

/**
 * 회원을 조회한다.
 */
public interface MemberFinder {

    Member find(Long memberId);
}
