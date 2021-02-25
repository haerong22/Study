package com.example.apptest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;

// 확장 모델을 필드에서 등록
class StudyExtensionTest_2 {

    @RegisterExtension
    static FindSlowTestExtension findSlowTestExtension
            = new FindSlowTestExtension(1000L);

    @SlowTest
    void test_38() throws InterruptedException {
        Thread.sleep(1500L);
        System.out.println("extension test: @SlowTest");
    }

    @Test
    void test_37() {
        System.out.println("extension test: fast test");
    }

    @Test
    void test_36() throws InterruptedException {
        Thread.sleep(1500L);
        System.out.println("extension test: slow test");
    }
}


// 확장 모델을 어노테이션으로 사용
@ExtendWith(FindSlowTestExtension_2.class)
public class StudyExtensionTest {

    @SlowTest
    void test_35() throws InterruptedException {
        Thread.sleep(1500L);
        System.out.println("extension test: @SlowTest");
    }

    @Test
    void test_34() {
        System.out.println("extension test: fast test");
    }

    @Test
    void test_33() throws InterruptedException {
        Thread.sleep(1500L);
        System.out.println("extension test: slow test");
    }
}
