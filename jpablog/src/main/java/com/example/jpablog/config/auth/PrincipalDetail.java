package com.example.jpablog.config.auth;

import com.example.jpablog.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayDeque;
import java.util.Collection;

public class PrincipalDetail implements UserDetails {
    private final User user;

    public PrincipalDetail(User user) {
        this.user = user;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    // 계정 만료 여부 ( false : 만료 )
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정이 잠금 여부 ( false : 잠김 )
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 비밀번호 만류 여부 ( false : 만료 )
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정 활성화 여부 ( false : 비활성화 )
    @Override
    public boolean isEnabled() {
        return true;
    }

    // 계정의 권한 목록 리턴
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collections = new ArrayDeque<>();
        collections.add(() -> "ROLE_" + user.getRole());
        return collections;
    }
}
