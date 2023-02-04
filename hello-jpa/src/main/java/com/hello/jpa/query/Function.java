package com.hello.jpa.query;

import com.hello.jpa.query.entity.Member;
import com.hello.jpa.query.entity.MemberType;
import com.hello.jpa.query.entity.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class Function {

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
            member2.setName("hello world");
            member2.setAge(70);
            member2.setTeam(team);
            member2.setMemberType(MemberType.USER);

            em.persist(member2);

            em.flush();
            em.clear();

//            String query = "select concat('a', 'b') from Member m";
//            String query = "select substring(m.name, 2, 3) from Member m";
//            String query = "select locate('he', m.name) from Member m";
//            String query = "select size(t.members) from Team t";
//            String query = "select function('group_concat', m.name) from Member m";
            String query = "select group_concat(m.name) from Member m";

            List<String> result = em.createQuery(
                            query,
                            String.class
                    )
                    .getResultList();

//            List<Integer> result = em.createQuery(
//                            query,
//                            Integer.class
//                    )
//                    .getResultList();

            result.forEach(System.out::println);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        emf.close();
    }
}
