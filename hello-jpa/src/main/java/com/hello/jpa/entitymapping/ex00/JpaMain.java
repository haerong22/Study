package com.hello.jpa.entitymapping.ex00;

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
            Member member = new Member("name");
            Member member2 = new Member("name2");
            em.persist(member);
            em.persist(member2);
            tx.commit();

            em.createQuery("select m from Member m").getResultList()
                    .forEach(System.out::println);

        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
