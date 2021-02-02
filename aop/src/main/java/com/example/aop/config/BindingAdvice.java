package com.example.aop.config;

import com.example.aop.domain.CommonDto;
import io.sentry.Sentry;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

// @Configuration -> @Controller , @Service, @Repository -> @Component
@Component
@Aspect
@Slf4j
public class BindingAdvice {

//    private static final Logger log = LoggerFactory.getLogger(BindingAdvice.class);

    @Before("execution(* com.example.aop.web..*Controller.*(..))")
    public void before() {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        System.out.println("전처리 로그");
    }
    @After("execution(* com.example.aop.web..*Controller.*(..))")
    public void after() {
        System.out.println("후처리 로그");
    }


    @Around("execution(* com.example.aop.web..*Controller.*(..))")
    public Object validCheck(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String type = proceedingJoinPoint.getSignature().getDeclaringTypeName();
        String method = proceedingJoinPoint.getSignature().getName();
        System.out.println(type);
        System.out.println(method);

        Object[] args = proceedingJoinPoint.getArgs();
        Map<String, String> errorMap = new HashMap<>();

//        for (Object arg : args) {
//            if (arg instanceof BindingResult) {
//                BindingResult bindingResult = (BindingResult) arg;
//                if (bindingResult.hasErrors()) {
//                    for (FieldError error : bindingResult.getFieldErrors()) {
//                        errorMap.put(error.getField(), error.getDefaultMessage());
//                    }
//                }
//            }
//        }

        Arrays.stream(args)
                .filter(v -> v instanceof BindingResult)
                .filter(v -> ((BindingResult) v).hasErrors())
                .forEach(v -> ((BindingResult) v).getFieldErrors()
                        .forEach(error -> {
                            errorMap.put(error.getField(), error.getDefaultMessage());
                            String msg = type + "." + method + "() => 필드 : " + error.getField() + ", 메시지 : " + error.getDefaultMessage();
                            log.warn(msg);
                            Sentry.captureMessage(msg);
                        }));

        return errorMap.isEmpty() ? proceedingJoinPoint.proceed() : new CommonDto<>(errorMap, HttpStatus.BAD_REQUEST.value());
    }
}


