package com.example.springdatajpa.repository;

import com.example.springdatajpa.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    @Query(name = "Member.findByUsername") // named query 가 있는지 먼저 찾고(domain.methodName) -> query creation
    List<Member> findByUsername(@Param("username") String username);
}
