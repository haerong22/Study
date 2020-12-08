package com.spring.security1.config.auth;

import com.spring.security1.model.User;
import com.spring.security1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// 시큐리티 설정에서 loginProcessingUrl("/login);
// /login 요청이 들어오면 자동으로 UserDetailsService 타입으로 IoC되어 있는
// loadUserByUsername 함수가 실행 된다.
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userEntity = userRepository.findByUsername(username);
        System.out.println(username);
        if(userEntity != null) {
            return new PrincipalDetails(userEntity); // 시큐리티 session 내부에 Authentication 내부에 UserDetail 이 들어간다.
        }
        return null;
    }
}
