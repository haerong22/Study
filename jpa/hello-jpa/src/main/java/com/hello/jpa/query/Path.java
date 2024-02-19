package com.hello.jpa.query;

import com.hello.jpa.query.entity.Member;
import com.hello.jpa.query.entity.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class Path {

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
            member.setName("member1");
            member.setTeam(team);

            em.persist(member);

            Member member2 = new Member();
            member2.setName("member2");
            member2.setTeam(team);

            em.persist(member2);

            em.flush();
            em.clear();

            List<String> result = em.createQuery(
                            "select m.name from Team t join t.members m",
                            String.class
                    )
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
