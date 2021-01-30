package com.example.jpablog.config.auth;

import com.example.jpablog.model.User;
import com.example.jpablog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    // 스프링이 로그인 요청시 username, password 변수 체크
    // password 는 스프링 시큐리티가 알아서 처리
    // username 은 DB에 있는지 체크
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User principal = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다."));

        return new PrincipalDetail(principal); // 시큐리티 세션에 유저 정보 저장
    }
}
