package com.example.aop.proxyvs;

import com.example.aop.member.MemberService;
import com.example.aop.member.MemberServiceImpl;
import com.example.aop.proxyvs.code.ProxyDIAspect;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
//@SpringBootTest(properties = {"spring.aop.proxy-target-class=false"}) // JDK 동적 프록시
//@SpringBootTest(properties = {"spring.aop.proxy-target-class=true"}) // CGLIB 프록시
@SpringBootTest
@Import(ProxyDIAspect.class)
public class ProxyDITest {

    @Autowired
    MemberService memberService;

    /**
     * JDK 동적 프록시 생성 시 타입 캐스트 불가능 (인터페이스 기반이므로 구체클래스를 알 수 없다.)
     */
    @Autowired
    MemberServiceImpl memberServiceImpl;

    @Test
    void go() {
        log.info("memberService class={}", memberService.getClass());
        log.info("memberServiceImpl class={}", memberServiceImpl.getClass());
        memberService.hello("hello");
    }
}
