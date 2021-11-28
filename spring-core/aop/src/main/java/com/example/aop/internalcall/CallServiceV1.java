package com.example.aop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 프록시 내부 호출 해결 1. 자기 자신 주입
 */
@Slf4j
@Component
public class CallServiceV1 {

   // 스프링 컨테이너에서 주입 받으면 프록시 주입
   private CallServiceV1 callServiceV1;

    // 자기 자신을 주입하므로 생성자 주입 시 순환 참조가 일어 나므로 세터로 주입
    @Autowired
    public void setCallServiceV1(CallServiceV1 callServiceV1) {
        this.callServiceV1 = callServiceV1;
    }

    public void external() {
        log.info("call external");
        callServiceV1.internal(); // 외부 메소드 호출로 변경
    }

    public void internal() {
        log.info("call internal");
    }
}
