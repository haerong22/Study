package com.example.restcontroller.common.aop;

import com.example.restcontroller.logs.service.LogService;
import com.example.restcontroller.user.entity.User;
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
public class LoginLogger {

    private final LogService logService;

    @Around("execution(* com.example.restcontroller..*.*Service*.*(..))")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {

        log.info("############################################################");
        log.info("서비스 호출 전!");
        log.info("############################################################");

        Object result = joinPoint.proceed();

        if ("login".equals(joinPoint.getSignature().getName())) {
            StringBuilder sb = new StringBuilder();
            sb.append("\n")
                    .append("함수명 : ")
                    .append(joinPoint.getSignature().getDeclaringType())
                    .append(".")
                    .append(joinPoint.getSignature().getName())
                    .append("\n")
                    .append("매개변수 : ");

            Object[] args = joinPoint.getArgs();
            Arrays.stream(args).forEach((v) -> {
                if (v instanceof UserLogin) {
                    sb.append(v.toString())
                            .append("\n")
                            .append("리턴값 : ")
                            .append(result.toString());
                }
            });

            logService.add(sb.toString());
            log.info(sb.toString());
        }

        log.info("############################################################");
        log.info("서비스 호출 후!");
        log.info("############################################################");

        return result;
    }
}
