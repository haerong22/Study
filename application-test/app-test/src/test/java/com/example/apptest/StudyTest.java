package com.example.apptest;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class StudyTest {

    @Test
    void create() {
        Study study = new Study();
        assertNotNull(study);
        System.out.println("create 테스트");
    }

    @Test
    void create1() {
        System.out.println("create1 테스트");
    }

    // @Disabled : 모든 테스트 실행 시 테스트 제외
    @Test
    @Disabled
    void create2() {
        System.out.println("create2 테스트");
    }

    /**
     * 모든 테스트 실행 전에 한번 실행
     * 반드시 static 메소드 사용해야 하며 리턴타입은 void
     * private 은 사용 불가, default 는 가능
     */
    @BeforeAll
    static void beforeAll() {
        System.out.println("before all");
    }

    /**
     * 모든 테스트 실행 후에 한번 실행
     * 반드시 static 메소드 사용해야 하며 리턴타입은 void
     */
    @AfterAll
    static void afterAll() {
        System.out.println("after all");
    }

    /**
     * 각 테스트 실행 전에 한번씩 실행
     */
    @BeforeEach
    void beforeEach() {
        System.out.println("before each");
    }

    /**
     * 각 테스트 실행 후에 한번씩 실행
     */
    @AfterEach
    void afterEach() {
        System.out.println("after each");
    }
}