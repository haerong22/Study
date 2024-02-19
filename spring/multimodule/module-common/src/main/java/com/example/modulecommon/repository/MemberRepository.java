package com.example.modulecommon.repository;

import com.example.modulecommon.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
