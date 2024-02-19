package com.hello.jpa.query;

import com.hello.jpa.query.entity.Member;
import com.hello.jpa.query.entity.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class FetchJoin {

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

//            List<Member> result = em.createQuery(
//                            "select m from Member m join fetch m.team",
//                            Member.class
//                    )
//                    .getResultList();
//
//            result.forEach(m -> {
//                System.out.println("m.getName() = " + m.getName() + ", " + m.getTeam().getName());
//            });
//
//            List<Team> result2 = em.createQuery(
//                            "select t from Team t join fetch t.members",
//                            Team.class
//                    )
//                    .getResultList();
//
//            result2.forEach(t -> {
//                System.out.println("team = " + t.getName() + ", members = " + t.getMembers().size());
//            });
//
//            List<Team> result3 = em.createQuery(
//                            "select distinct t from Team t join fetch t.members",
//                            Team.class
//                    )
//                    .getResultList();
//
//            result3.forEach(t -> {
//                System.out.println("team = " + t.getName() + ", members = " + t.getMembers().size());
//            });

            List<Team> result4 = em.createQuery(
                            "select t from Team t",
                            Team.class
                    )
                    .setFirstResult(0)
                    .setMaxResults(2)
                    .getResultList();

            result4.forEach(t -> {
                System.out.println("team = " + t.getName() + ", members = " + t.getMembers().size());
                t.getMembers().forEach(System.out::println);
            });

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
