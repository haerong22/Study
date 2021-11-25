package com.example.aop.pointcut;

import com.example.aop.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

/**
 * application.properties
 * spring.aop.proxy-target-class=true   CGLIB (default)
 * spring.aop.proxy-target-class=false  JDK 동적 프록시
 */
@Slf4j
@Import(ThisTargetTest.ThisTargetAspect.class)
@SpringBootTest(properties = "spring.aop.proxy-target-class=false")
public class ThisTargetTest {

    @Autowired
    MemberService memberService;

    @Test
    void success() {
        log.info("memberService Proxy={}", memberService.getClass());
        memberService.hello("helloA");
    }

    @Slf4j
    @Aspect
    static class ThisTargetAspect {

        // 부모 타입 허용
        @Around("this(com.example.aop.member.MemberService)")
        public Object doThisInterface(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[this-interface] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }

        @Around("target(com.example.aop.member.MemberService)")
        public Object doTargetInterface(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[target-interface] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }

        // 부모 타입 허용
        @Around("this(com.example.aop.member.MemberServiceImpl)")
        public Object doThis(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[this-impl] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }

        @Around("target(com.example.aop.member.MemberServiceImpl)")
        public Object doTarget(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[target-impl] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }
    }
}
