package com.hello.jpa.valuetype.ex02;

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

            Member member = new Member();
            member.setName("name");
            member.setHomeAddress(new Address("city", "address", "zipcode"));

            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("피자");
            member.getFavoriteFoods().add("족발");

            member.getAddressHistory().add(new Address("old", "old", "old"));
            member.getAddressHistory().add(new Address("old2", "old2", "old2"));

            em.persist(member);

            em.flush();
            em.clear();

            System.out.println("============== START ==============");
            Member findMember = em.find(Member.class, member.getId());

            findMember.getAddressHistory().forEach(System.out::println);
            findMember.getFavoriteFoods().forEach(System.out::println);

            findMember.setHomeAddress(new Address("new city", "new address", "new zipcode"));

            // 치킨 -> 한식
            findMember.getFavoriteFoods().remove("치킨");
            findMember.getFavoriteFoods().add("한식");

            findMember.getAddressHistory().remove(new Address("old", "old", "old"));
            findMember.getAddressHistory().add(new Address("old3", "old3", "old3"));

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
