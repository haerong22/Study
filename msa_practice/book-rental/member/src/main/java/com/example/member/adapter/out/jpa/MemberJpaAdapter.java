package com.example.member.adapter.out.jpa;

import com.example.member.adapter.out.jpa.entity.MemberJpaEntity;
import com.example.member.adapter.out.jpa.repository.MemberJpaRepository;
import com.example.member.application.port.out.MemberPort;
import com.example.member.domain.Member;
import com.example.member.domain.vo.IDName;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
@RequiredArgsConstructor
public class MemberJpaAdapter implements MemberPort {

    private final MemberJpaRepository memberJpaRepository;

    @Override
    public Member getMember(long memberNo) {
        return memberJpaRepository.findById(memberNo)
                .map(MemberJpaEntity::toMember)
                .orElse(null);
    }

    @Override
    public Member getMemberByIdName(IDName idName) {
        return memberJpaRepository.findMemberJpaEntityByIdAndName(idName.getId(), idName.getName())
                .map(MemberJpaEntity::toMember)
                .orElse(null);
    }

    @Override
    public Member save(Member member) {
        return memberJpaRepository.save(MemberJpaEntity.fromMember(member)).toMember();
    }
}
