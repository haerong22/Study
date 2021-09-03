package com.example.springdatajpa.repository;

import com.example.springdatajpa.entity.Member;
import com.example.springdatajpa.entity.Team;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;

public class MemberSpec {
    public static Specification<Member> teamName(final String teamName) {
        return (root, query, criteriaBuilder) -> {
            if (ObjectUtils.isEmpty(teamName)) {
                return null;
            }

            Join<Member, Team> t = root.join("team", JoinType.INNER); //회원과 조인
            return criteriaBuilder.equal(t.get("name"), teamName);
        };
    }
    public static Specification<Member> username(final String username) {
        return (root, query, criteriaBuilder) -> {
            if (ObjectUtils.isEmpty(username)) {
                return null;
            }

            return criteriaBuilder.equal(root.get("username"), username);
        };
    }
}


