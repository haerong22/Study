package org.example.splearn.application.provided;

import org.example.splearn.domain.Member;

/**
 * 회원을 조회한다.
 */
public interface MemberFinder {

    Member find(Long memberId);
}
