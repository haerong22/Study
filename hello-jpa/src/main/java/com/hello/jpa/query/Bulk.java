package com.hello.jpa.query;

import com.hello.jpa.query.entity.Member;
import com.hello.jpa.query.entity.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Bulk {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Team team = new Team();
            team.setName("team1");
            em.persist(team);

            Team team2 = new Team();
            team2.setName("team2");
            em.persist(team2);

            Member member = new Member();
            member.setName("member1");
            member.setTeam(team);
            em.persist(member);

            Member member2 = new Member();
            member2.setName("member2");
            member2.setTeam(team);
            em.persist(member2);

            Member member3 = new Member();
            member3.setName("member3");
            member3.setTeam(team2);
            em.persist(member3);

            // flush 자동 호출
            int resultCount = em.createQuery("update Member m set m.age = 20")
                    .executeUpdate();

            System.out.println("resultCount = " + resultCount);

            // 영속성 컨텍스트에 남아 있으므로 초기화 해야함
            Member findMember = em.find(Member.class, member.getId());
            System.out.println("findMember = " + findMember);

            em.clear();
            Member findMember2 = em.find(Member.class, member.getId());
            System.out.println("findMember = " + findMember2);

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
