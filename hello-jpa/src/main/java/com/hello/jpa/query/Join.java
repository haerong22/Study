package com.hello.jpa.query;

import com.hello.jpa.query.entity.Member;
import com.hello.jpa.query.entity.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class Join {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            for (int i = 0; i < 10; i++) {
                Team team = new Team();
                team.setName("team" + i);
                em.persist(team);

                Member member = new Member();
                member.setName("member" + i);
                member.setAge(i);
                member.setTeam(team);

                em.persist(member);
            }

            em.flush();
            em.clear();

            List<Member> result = em.createQuery(
                            "select m from Member m inner join m.team t",
                            Member.class
                    )
                    .getResultList();

            List<Member> result2 = em.createQuery(
                            "select m from Member m left outer join m.team t",
                            Member.class
                    )
                    .getResultList();

            List<Member> result3 = em.createQuery(
                            "select m from Member m, Team t",
                            Member.class
                    )
                    .getResultList();

            List<Member> result4 = em.createQuery(
                            "select m from Member m left join m.team t on t.name='team2'",
                            Member.class
                    )
                    .getResultList();

            List<Member> result5 = em.createQuery(
                            "select m from Member m left join Team t on m.name = t.name",
                            Member.class
                    )
                    .getResultList();

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
