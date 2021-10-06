package com.example.basic;

import com.example.basic.member.MemberRepository;
import com.example.basic.member.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        basePackages = "com.example.basic", // default : @ComponentScan 의 위치  패키지
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoAppConfig {
/*
    @Bean(name = "memoryMemberRepository") // 수동 빈, 자동 빈 이름 같을 경우 수동 빈이 우선순위를 가진다.( 최근 스프링 부트는 에러 )
    MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }
*/
}
