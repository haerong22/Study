package com.hello.jpa.query;

import com.hello.jpa.query.entity.Address;
import com.hello.jpa.query.entity.Member;
import com.hello.jpa.query.entity.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class Projection {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member = new Member();
            member.setName("member");
            member.setAge(10);
            em.persist(member);

            // 엔티티 프로젝션
            Member result = em.createQuery("select m from Member m", Member.class)
                    .getSingleResult();

            result.setAge(20);

            em.createQuery("select t from Member m join m.team t", Team.class)
                    .getResultList();

            // 임베디드 타입 프로젝션
            em.createQuery("select o.address from Order o", Address.class)
                    .getResultList();

            // 스칼라 타입 프로젝션
            em.createQuery("select distinct m.name, m.age from Member m")
                    .getResultList();

            // 여러 값 조회
            List resultList = em.createQuery("select m.name, m.age from Member m")
                    .getResultList();


            Object[] result2 = (Object[]) resultList.get(0);
            System.out.println("name = " + result2[0]);
            System.out.println("age = " + result2[1]);

            // DTO
            List<MemberDto> memberDto = em.createQuery("select new com.hello.jpa.query.MemberDto(m.name, m.age) from Member m")
                    .getResultList();

            memberDto.forEach(System.out::println);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
