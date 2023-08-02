package org.example.common;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class LoggingAspect {

    private final LoggingProducer loggingProducer;

    @Before("execution(* org.example.*.adapter.in.web.*.*(..))")
    public void beforeMethodExecution(@NotNull JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        log.info("Before executing method: {}",methodName);
        loggingProducer.sendMessage("logging", "Before executing method: " + methodName);
    }
}
