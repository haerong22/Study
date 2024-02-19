package com.example.restcontroller.common.aop;

import com.example.restcontroller.logs.service.LogService;
import com.example.restcontroller.user.model.UserLogin;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Component
@Aspect
@RequiredArgsConstructor
public class BoardLogger {

    private final LogService logService;

    @Around("execution(* com.example.restcontroller..*.*Controller.*(..))")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {

        log.info("############################################################");
        log.info("컨트롤러 호출 전!");
        log.info("############################################################");

        Object result = joinPoint.proceed();

        if (joinPoint.getSignature().getDeclaringTypeName().contains("ApiBoardController")
            && "chapter4_3".equals(joinPoint.getSignature().getName())) {
            StringBuilder sb = new StringBuilder();

            sb.append("파라미터 : ");
            Object[] args = joinPoint.getArgs();
            Arrays.stream(args).forEach((v) -> {
                sb.append(v.toString());
            });
            sb.append("결과 : ");
            sb.append(result.toString());

            logService.add(sb.toString());
            log.info(sb.toString());
        }

        log.info("############################################################");
        log.info("컨트롤러 호출 후!");
        log.info("############################################################");

        return result;
    }
}
