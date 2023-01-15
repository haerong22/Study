package com.hello.jpa.relationmapping.ex00;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member1 = new Member();
            member1.setName("a");
            member1.setTeam(team);

            Member member2 = new Member();
            member2.setName("b");
            member2.setTeam(team);
            em.persist(member1);
            em.persist(member2);

            team.addMember(member1);
            team.addMember(member2);

            em.flush();
            em.clear();

            Member findMember = em.find(Member.class, member1.getId());
            Team findTeam = findMember.getTeam();
            System.out.println("findTeam.getName() = " + findTeam.getName());

            findTeam.getMembers().forEach(mem -> System.out.println("mem.getName() = " + mem.getName()));
            
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
