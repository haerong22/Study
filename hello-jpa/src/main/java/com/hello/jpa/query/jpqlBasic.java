package com.hello.jpa.query;

import com.hello.jpa.query.entity.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class jpqlBasic {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member = new Member();
            member.setName("member");
            em.persist(member);
            Member member2 = new Member();
            member2.setName("kim2");
            em.persist(member2);

            List<Member> result = em.createQuery(
                    "select m from Member m where m.name like '%kim%'",
                    Member.class
            ).getResultList();

            result.forEach(System.out::println);

            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Member> query = cb.createQuery(Member.class);
            Root<Member> m = query.from(Member.class);
            CriteriaQuery<Member> cq = query.select(m).where(cb.equal(m.get("name"), "kim"));

            List<Member> result2 = em.createQuery(cq).getResultList();
            result2.forEach(System.out::println);

            List<Member> result3 = em.createNativeQuery("select * from member", Member.class).getResultList();
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
