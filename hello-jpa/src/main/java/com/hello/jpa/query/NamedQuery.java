package com.hello.jpa.query;

import com.hello.jpa.query.entity.Member;
import com.hello.jpa.query.entity.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class NamedQuery {

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

            em.flush();
            em.clear();

            List<Member> result = em.createNamedQuery("Member.findByName", Member.class)
                    .setParameter("name", "member2")
                    .getResultList();

            result.forEach(System.out::println);

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
