package com.example.aop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CallServiceV0 {

    /**
     * 객체에서 내부 메소드를 호출 하는 경우 프록시가 적용 되지 않는 문제 발생
     */
    public void external() {
        log.info("call external");
        internal();
    }

    public void internal() {
        log.info("call internal");
    }
}
