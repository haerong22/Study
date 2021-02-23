package com.example.apptest;

import org.junit.jupiter.api.*;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StudyTest {

    @Test
    @DisplayName("assertTimeout test")
    void test_4() {
        assertTimeoutPreemptively(Duration.ofMillis(100), () -> {
            Thread.sleep(300);
            new Study(10);
        });

        // assertTimeout( 시간, 실행구문 )
        // 실행 구문이 끝날 때 까지 기다렸다가 결과를 출력한다.
        assertTimeout(Duration.ofMillis(100), () -> {
            Thread.sleep(300);
            new Study(10);
        });
    }

    @Test
    @DisplayName("assertThrows test")
    void test_3() {
        // 리턴 값은 해당 예외 타입이다.
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> new Study(-10));

        // 에러 메시지 확인도 가능하다
        assertEquals("limit은 0보다 커야 한다.", exception.getMessage());
    }

    @Test
    @DisplayName("assertAll test")
    void test_2() {
        Study study = new Study(-10);

        assertAll(
                () -> assertTrue(study.getLimit() > 0, "스터디 최대 참석 가능 인원은 0보다 커야 한다."),
                () -> assertEquals(StudyStatus.DRAFT, study.getStatus(), "스터디를 처음 만들면 status가 DRAFT 이어야 한다.")
        );
    }

    @Test
    @DisplayName("assertTrue test")
    void test_1() {
        Study study = new Study(10);
        // assertTrue( 조건, 실패시 출력 메시지 )
        assertTrue(study.getLimit() > 0, "스터디 최대 참석 가능 인원은 0보다 커야 한다.");
    }

    @Test
    @DisplayName("assertEquals test")
    void test() {
        Study study = new Study();
        // assertEquals( 기대값, 실제값, 실패시 메시지 )

        assertEquals(StudyStatus.DRAFT, study.getStatus(), "스터디를 처음 만들면 status가 DRAFT 이어야 한다.");
    }

    @Test
    void create_new_study() {
        Study study = new Study();
        assertNotNull(study);
        System.out.println("create 테스트");
    }

    @Test
    @DisplayName("스터디 생성하기!! 💖")
    void create_new_study_2() {
        System.out.println("create1 테스트");
    }

    // @Disabled : 모든 테스트 실행 시 테스트 제외
    @Test
    @Disabled
    void create2() {
        System.out.println("create2 테스트");
    }
//
//    /**
//     * 모든 테스트 실행 전에 한번 실행
//     * 반드시 static 메소드 사용해야 하며 리턴타입은 void
//     * private 은 사용 불가, default 는 가능
//     */
//    @BeforeAll
//    static void beforeAll() {
//        System.out.println("before all");
//    }
//
//    /**
//     * 모든 테스트 실행 후에 한번 실행
//     * 반드시 static 메소드 사용해야 하며 리턴타입은 void
//     */
//    @AfterAll
//    static void afterAll() {
//        System.out.println("after all");
//    }
//
//    /**
//     * 각 테스트 실행 전에 한번씩 실행
//     */
//    @BeforeEach
//    void beforeEach() {
//        System.out.println("before each");
//    }
//
//    /**
//     * 각 테스트 실행 후에 한번씩 실행
//     */
//    @AfterEach
//    void afterEach() {
//        System.out.println("after each");
//    }
}