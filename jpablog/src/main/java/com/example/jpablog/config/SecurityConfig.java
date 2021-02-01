package com.example.jpablog.config;

import com.example.jpablog.config.auth.PrincipalDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration // bean 등록
@RequiredArgsConstructor
@EnableWebSecurity // 시큐리티 필터 등록
@EnableGlobalMethodSecurity(prePostEnabled = true) // 특정 주소 접근시 먼저 권한 및 인증 체크
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PrincipalDetailService principalDetailService;

    @Bean
    public BCryptPasswordEncoder encodePWD() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    // 시큐리티가 대신 로그인 할때 어떤 해시를 사용했는지 알아야 DB와 비교가능
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable() // csrf 토큰 비활성화
            .authorizeRequests() // 요청에 대한 인증
                .antMatchers("/", "/auth/**", "/js/**", "/css/**", "/images/**", "/dummy/**").permitAll() // 해당 주소로 들어오면 모두 허가
                .anyRequest().authenticated() // 나머지 요청은 모두 인증을 거쳐야 함
            .and()
                .formLogin().loginPage("/auth/loginForm") // 로그인 폼 지정
                .loginProcessingUrl("/auth/loginProc") // 해당 주소로 들어오는 로그인 요청을 가로채서 스프링 시큐리티가 로그인
                .defaultSuccessUrl("/"); // 정상 로그인 완료 후 이동 URL
    }
}
