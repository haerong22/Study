package com.example.jdbc.repository;

import com.example.jdbc.domain.Member;

import java.sql.SQLException;

/**
 * 체크예외는 특정 예외에 종속적인 인터페이스가 되어 버린다.
 */
public interface MemberRepositoryEx {
    Member save(Member member) throws SQLException;
    Member findById(String memberId) throws SQLException;
    void update(String memberId, int money) throws SQLException;
    void delete(String memberId) throws SQLException;
}
