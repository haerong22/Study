package com.hello.jpa.context;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class PersistenceContext {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            // 비영속
            Member member = new Member(150L, "memberA");
            Member member2 = new Member(160L, "memberB");

            // 영속
            System.out.println("=== BEFORE ===");
            em.persist(member);
            em.persist(member2);
            System.out.println("=== AFTER ===");

            // 1차 캐시
            Member findMember = em.find(Member.class, 150L);
            System.out.println("findMember.getId() = " + findMember.getId());
            System.out.println("findMember.getName() = " + findMember.getName());
            Member findMember2 = em.find(Member.class, 150L);

            // 엔티티 동일성 보장
            System.out.println("result = " + (findMember == findMember2));

            // 더티 체킹
            Member update = em.find(Member.class, 160L);
            update.setName("update name");

            // 쓰기 지연 저장소에 담긴 쿼리 실행
//            em.flush();

            // 쓰기 지연 - 커밋하는 순간 insert
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
