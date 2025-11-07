package org.example.splearn.application.member.required;

import org.example.splearn.domain.shared.Email;
import org.example.splearn.domain.member.Member;
import org.springframework.data.repository.Repository;

import java.util.Optional;

/**
 * 회원 정보를 저장하거나 조회한다.
 */
public interface MemberRepository extends Repository<Member, Long> {

    Member save(Member member);

    Optional<Member> findByEmail(Email email);

    Optional<Member> findById(Long memberId);
}
