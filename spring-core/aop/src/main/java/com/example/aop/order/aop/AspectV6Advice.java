package com.example.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

@Slf4j
@Aspect
public class AspectV6Advice {

    // com.example.aop.order 패키지와 하위 패키지 이면서 클래스 이름 패턴이 *Service
    @Around("com.example.aop.order.aop.Pointcuts.orderAndService()")
    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {

        try {
            // @Before
            log.info("[트랜잭션 시작] {}",joinPoint.getSignature());
            Object result = joinPoint.proceed();

            // @AfterReturning
            log.info("[트랜잭션 커밋] {}",joinPoint.getSignature());
            return result;
        } catch (Exception e) {
            // @AfterThrowing
            log.info("[트랜잭션 롤백] {}",joinPoint.getSignature());
            throw e;
        } finally {
            // @After
            log.info("[리소스 릴리즈] {}",joinPoint.getSignature());
        }
    }

    @Before("com.example.aop.order.aop.Pointcuts.orderAndService()")
    public void doBefore(JoinPoint joinPoint) {
        log.info("[before] {}", joinPoint.getSignature());
    }

    @AfterReturning(value = "com.example.aop.order.aop.Pointcuts.orderAndService()", returning = "result")
    public void doReturn(JoinPoint joinPoint, Object result) {
        log.info("[return] {} return {}", joinPoint.getSignature(), result);
    }

    @AfterReturning(value = "com.example.aop.order.aop.Pointcuts.allOrder()", returning = "result")
    public void doReturn2(JoinPoint joinPoint, String result) {
        log.info("[return2] {} return2 {}", joinPoint.getSignature(), result);
    }

    @AfterThrowing(value = "com.example.aop.order.aop.Pointcuts.orderAndService()", throwing = "ex")
    public void doReturn(JoinPoint joinPoint, Exception ex) {
        log.info("[ex] {} message {}", joinPoint.getSignature(), ex);
    }

    @After("com.example.aop.order.aop.Pointcuts.orderAndService()")
    public void doAfter(JoinPoint joinPoint) {
        log.info("[after] {}", joinPoint.getSignature());
    }
}
