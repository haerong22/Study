package com.hello.jpa.query;

import com.hello.jpa.query.entity.Member;
import com.hello.jpa.query.entity.MemberType;
import com.hello.jpa.query.entity.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class Condition {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Team team = new Team();
            team.setName("team");
            em.persist(team);

            Member member = new Member();
            member.setName("member");
            member.setAge(10);
            member.setTeam(team);
            member.setMemberType(MemberType.ADMIN);

            em.persist(member);

            Member member2 = new Member();
            member2.setName(null);
            member2.setAge(70);
            member2.setTeam(team);
            member2.setMemberType(MemberType.USER);

            em.persist(member2);

            em.flush();
            em.clear();

            List<String> result = em.createQuery(
                            "select " +
                                    "case when m.age <= 10 then '학생요금' " +
                                    "when m.age >= 60 then '경로요금' else '일반요금' end  from Member m",
                            String.class
                    )
                    .getResultList();

            result.forEach(System.out::println);

            List<String> result2 = em.createQuery(
                            "select coalesce(m.name, '이름 없는 회원') from Member m",
                            String.class
                    )
                    .getResultList();

            result2.forEach(System.out::println);

            List<String> result3 = em.createQuery(
                            "select nullif(m.name, 'member') from Member m",
                            String.class
                    )
                    .getResultList();

            result3.forEach(System.out::println);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
