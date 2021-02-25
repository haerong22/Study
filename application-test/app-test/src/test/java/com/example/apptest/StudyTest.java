package com.example.apptest;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.*;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;
import org.junit.jupiter.params.provider.*;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StudyTest {

    @Order(2)
    @Test
    @DisplayName("order test2")
    void test_29() {
        System.out.println(this);
        System.out.println("테스트 2");
    }

    @Order(1)
    @Test
    @DisplayName("order test1")
    void test_28() {
        System.out.println(this);
        System.out.println("테스트 1");
    }

//    int value = 0;
//
//    @Test
//    @DisplayName("instance test2")
//    void test_27() {
//        System.out.println(this);
//        System.out.println("value: " + value++);
//    }
//
//    @Test
//    @DisplayName("instance test1")
//    void test_26() {
//        System.out.println(this);
//        System.out.println("value: " + value++);
//    }


//    @DisplayName("ParameterizedTest test")
//    @ParameterizedTest
//    @CsvSource({"10, '자바'", "20, 스프링"})
//    void test_25(@AggregateWith(StudyAggregator.class) Study study) {
//        System.out.println(study);
//    }
//
//    static class StudyAggregator implements ArgumentsAggregator {
//        @Override
//        public Object aggregateArguments(ArgumentsAccessor a, ParameterContext parameterContext)
//                throws ArgumentsAggregationException {
//            return new Study(a.getInteger(0), a.getString(1));
//        }
//    }

//    @DisplayName("ParameterizedTest test")
//    @ParameterizedTest
//    @CsvSource({"10, '자바'", "20, 스프링"})
//    void test_24(ArgumentsAccessor a) {
//        Study study = new Study(a.getInteger(0), a.getString(1));
//        System.out.println(study);
//    }
//
//    @DisplayName("ParameterizedTest test")
//    @ParameterizedTest
//    @CsvSource({"10, '자바'", "20, 스프링"})
//    void test_23(Integer limit, String name) {
//        Study study = new Study(limit, name);
//        System.out.println(study);
//    }
//
//    @DisplayName("ParameterizedTest test")
//    @ParameterizedTest(name = "{index} {displayName} message={0}")
//    @ValueSource(ints = {10, 20, 30})
//    void test_22(@ConvertWith(StudyConverter.class) Study study) {
//        System.out.println(study.getLimit());
//    }
//
//    static class StudyConverter extends SimpleArgumentConverter {
//        @Override
//        protected Object convert(Object source, Class<?> targetType) throws ArgumentConversionException {
//            assertEquals(Study.class, targetType, "Can only convert to Study");
//            return new Study(Integer.parseInt(source.toString()));
//        }
//    }
//
//    @DisplayName("ParameterizedTest test")
//    @ParameterizedTest(name = "{index} {displayName} message={0}")
//    @ValueSource(strings = {"반복", "테스트", "데이터"})
//    @NullAndEmptySource
//    void test_21(String message) {
//        System.out.println("message : " + message);
//    }
//
//    @DisplayName("ParameterizedTest test")
//    @ParameterizedTest(name = "{index} {displayName} message={0}")
//    @ValueSource(strings = {"반복", "테스트", "데이터"})
//    void test_20(String message) {
//        System.out.println(message);
//    }
//
//    @RepeatedTest(value = 5, name = "{displayName}, {currentRepetition}/{totalRepetitions}")
//    @DisplayName("repeated test")
//    void test_19(RepetitionInfo repetitionInfo) {
//
//        System.out.println(
//                "test" + repetitionInfo.getCurrentRepetition() +
//                        "/" + repetitionInfo.getTotalRepetitions());
//    }
//
//    @RepeatedTest(5)
//    @DisplayName("repeated test")
//    void test_18(RepetitionInfo repetitionInfo) {
//
//        System.out.println(
//                "test" + repetitionInfo.getCurrentRepetition() +
//                "/" + repetitionInfo.getTotalRepetitions());
//    }
//
//    @RepeatedTest(5)
//    @DisplayName("repeated test")
//    void test_17() {
//        System.out.println("test");
//    }


//    @FastTest
//    @DisplayName("custom tag test")
//    void test_16() {
//        System.out.println("fast");
//        Study study = new Study(10);
//        assertTrue(study.getLimit() > 0, () -> "limit 는 0보다 커야한다.");
//    }
//
//    @SlowTest
//    @DisplayName("custom tag test")
//    void test_15() {
//        System.out.println("slow");
//        Study study = new Study(10);
//        assertTrue(study.getLimit() > 0, () -> "limit 는 0보다 커야한다.");
//    }

//    @Test
//    @DisplayName("tag test")
//    @Tag("fast")
//    void test_14() {
//        System.out.println("fast");
//        Study study = new Study(10);
//        assertTrue(study.getLimit() > 0, () -> "limit 는 0보다 커야한다.");
//    }
//
//    @Test
//    @DisplayName("tag test")
//    @Tag("slow")
//    void test_13() {
//        System.out.println("slow");
//        Study study = new Study(10);
//        assertTrue(study.getLimit() > 0, () -> "limit 는 0보다 커야한다.");
//    }

//    @Test
//    @DisplayName("DisabledIfEnvironmentVariable test")
//    @DisabledIfEnvironmentVariable(named = "TEST_ENV", matches = "KIM")
//    void test_12() {
//        System.out.println("테스트");
//        Study study = new Study(10);
//        assertTrue(study.getLimit() > 0, () -> "limit 는 0보다 커야한다.");
//    }
//
//    @Test
//    @DisplayName("EnabledIfEnvironmentVariable test")
//    @EnabledIfEnvironmentVariable(named = "TEST_ENV", matches = "KIM")
//    void test_11() {
//        System.out.println("테스트");
//        Study study = new Study(10);
//        assertTrue(study.getLimit() > 0, () -> "limit 는 0보다 커야한다.");
//    }

//    @Test
//    @DisplayName("EnabledOnJre test")
//    @EnabledOnJre({JRE.JAVA_8, JRE.JAVA_11})
//    void test_10() {
//        System.out.println("테스트");
//        Study study = new Study(10);
//        assertTrue(study.getLimit() > 0, () -> "limit 는 0보다 커야한다.");
//    }
//
//    @Test
//    @DisplayName("DisabledOnJre test")
//    @DisabledOnJre(JRE.JAVA_11)
//    void test_9() {
//        System.out.println("테스트");
//        Study study = new Study(10);
//        assertTrue(study.getLimit() > 0, () -> "limit 는 0보다 커야한다.");
//    }
//
//    @Test
//    @DisplayName("EnabledOnOs test")
//    @EnabledOnOs({OS.WINDOWS, OS.MAC})
//    void test_8() {
//        System.out.println("테스트");
//        Study study = new Study(10);
//        assertTrue(study.getLimit() > 0, () -> "limit 는 0보다 커야한다.");
//    }
//
//    @Test
//    @DisplayName("DisabledOnOs test")
//    @DisabledOnOs(OS.WINDOWS)
//    void test_7() {
//        System.out.println("테스트");
//        Study study = new Study(10);
//        assertTrue(study.getLimit() > 0, () -> "limit 는 0보다 커야한다.");
//    }

//    @Test
//    @DisplayName("assumingThat test")
//    void test_6() {
//        String test_env = System.getenv("TEST_ENV");
//        Assumptions.assumingThat(test_env.equals("KIM"), () -> {
//            System.out.println("env_kim");
//            Study study = new Study(10);
//            assertTrue(study.getLimit() > 0, () -> "limit 는 0보다 커야한다.");
//        });
//
//        Assumptions.assumingThat(test_env.equals("LEE"), () -> {
//            System.out.println("env_lee");
//            Study study = new Study(10);
//            assertTrue(study.getLimit() > 0, () -> "limit 는 0보다 커야한다.");
//        });
//    }
//
//    @Test
//    @DisplayName("assumeTrue test")
//    void test_5() {
//        String test_env = System.getenv("TEST_ENV");
//        System.out.println(test_env);
//        Assumptions.assumeTrue("kim".equalsIgnoreCase(test_env));
//
//        Study study = new Study(10);
//        assertTrue(study.getLimit() > 0, () -> "limit 는 0보다 커야한다.");
//    }
//
//    @Test
//    @DisplayName("assertTimeout test")
//    void test_4() {
//        assertTimeoutPreemptively(Duration.ofMillis(100), () -> {
//            Thread.sleep(300);
//            new Study(10);
//        });
//
//        // assertTimeout( 시간, 실행구문 )
//        // 실행 구문이 끝날 때 까지 기다렸다가 결과를 출력한다.
//        assertTimeout(Duration.ofMillis(100), () -> {
//            Thread.sleep(300);
//            new Study(10);
//        });
//    }
//
//    @Test
//    @DisplayName("assertThrows test")
//    void test_3() {
//        // 리턴 값은 해당 예외 타입이다.
//        IllegalArgumentException exception =
//                assertThrows(IllegalArgumentException.class, () -> new Study(-10));
//
//        // 에러 메시지 확인도 가능하다
//        assertEquals("limit은 0보다 커야 한다.", exception.getMessage());
//    }
//
//    @Test
//    @DisplayName("assertAll test")
//    void test_2() {
//        Study study = new Study(-10);
//
//        assertAll(
//                () -> assertTrue(study.getLimit() > 0, "스터디 최대 참석 가능 인원은 0보다 커야 한다."),
//                () -> assertEquals(StudyStatus.DRAFT, study.getStatus(), "스터디를 처음 만들면 status가 DRAFT 이어야 한다.")
//        );
//    }
//
//    @Test
//    @DisplayName("assertTrue test")
//    void test_1() {
//        Study study = new Study(10);
//        // assertTrue( 조건, 실패시 출력 메시지 )
//        assertTrue(study.getLimit() > 0, "스터디 최대 참석 가능 인원은 0보다 커야 한다.");
//    }
//
//    @Test
//    @DisplayName("assertEquals test")
//    void test() {
//        Study study = new Study();
//        // assertEquals( 기대값, 실제값, 실패시 메시지 )
//
//        assertEquals(StudyStatus.DRAFT, study.getStatus(), "스터디를 처음 만들면 status가 DRAFT 이어야 한다.");
//    }
//
//    @Test
//    void create_new_study() {
//        Study study = new Study();
//        assertNotNull(study);
//        System.out.println("create 테스트");
//    }
//
//    @Test
//    @DisplayName("스터디 생성하기!! 💖")
//    void create_new_study_2() {
//        System.out.println("create1 테스트");
//    }
//
//    // @Disabled : 모든 테스트 실행 시 테스트 제외
//    @Test
//    @Disabled
//    void create2() {
//        System.out.println("create2 테스트");
//    }
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