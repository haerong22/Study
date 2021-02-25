package com.example.apptest;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.lang.reflect.Method;

public class FindSlowTestExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback {

    private final long THRESHOLD;

    public FindSlowTestExtension(long THRESHOLD) {
        this.THRESHOLD = THRESHOLD;
    }

    // 테스트 실행 전에 실행하는 메소드
    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {
        // 스토어 생성
        ExtensionContext.Store store = getStore(context);

        // store 에 값 저장하기
        store.put("START_TIME", System.currentTimeMillis());
    }

    // 테스트 실행 후에 실행하는 메소드
    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        // 테스트 메소드 가져오기
        Method requiredTestMethod = context.getRequiredTestMethod();
        // 애노테이션
        SlowTest annotation = requiredTestMethod.getAnnotation(SlowTest.class);

        // 스토어 생성
        ExtensionContext.Store store = getStore(context);

        // 스토어에서 값을 꺼내 실행 시간 구하기
        long start_time = store.remove("START_TIME", long.class);
        long duration = System.currentTimeMillis() - start_time;

        // 메시지 출력력
       if (duration > THRESHOLD && annotation == null) {
            System.out.printf("Please consider mark method [%s] with @SlowTest.\n", requiredTestMethod.getName());
        }
    }

    // 스토어 생성 메소드
    private ExtensionContext.Store getStore(ExtensionContext context) {
        // 테스트 클래스 이름 가져오기
        String testClassName = context.getRequiredTestClass().getName();
        // 테스트 메소드 이름 가져오기
        String testMethodName = context.getRequiredTestMethod().getName();
        // 스토어 생성
        return context.getStore(ExtensionContext.Namespace.create(testClassName, testMethodName));
    }
}
