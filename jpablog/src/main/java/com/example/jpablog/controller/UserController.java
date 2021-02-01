package com.example.jpablog.controller;

import com.example.jpablog.model.KakaoProfile;
import com.example.jpablog.model.OAuthToken;
import com.example.jpablog.model.User;
import com.example.jpablog.service.KakaoLogin;
import com.example.jpablog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

// 인증이 안된 사용자들이 들어오는 경로 /auth/**
// "/" 요청시 index.jsp 허용
// static 이하에 있는 /js/**, /css/**, /image/** 허용

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder encoder;

    @GetMapping("/auth/joinForm")
    public String joinForm() {
        return "user/joinForm";
    }

    @GetMapping("/auth/loginForm")
    public String loginForm() {
        return "user/loginForm";
    }

    @GetMapping("/user/updateForm")
    public String updateForm() {
        return "user/updateForm";
    }

    @GetMapping("/auth/kakao/callback")
    public String kakaoCallback(String code) {
        // Retrofit2, OkHttp, RestTemplate, HttpsURLConnection 등이 있음
        KakaoLogin kakaoLogin = new KakaoLogin();

        OAuthToken token = kakaoLogin.getCode(code);
        KakaoProfile kakaoProfile = kakaoLogin.getKakaoProfile(token);
        String tempPassword = UUID.randomUUID().toString();
        String username = kakaoProfile.getKakao_account().getEmail() + "_" + kakaoProfile.getId();
        User user = userService.회원찾기(username);
        if (user.getUsername() == null) {
            User kakaoUser = User.builder()
                    .username(username)
                    .password(tempPassword)
                    .email(kakaoProfile.getKakao_account().getEmail())
                    .oauth("kakao")
                    .build();
            userService.회원가입(kakaoUser);
        } else {
            user.setPassword(encoder.encode(tempPassword));
        }

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(username, tempPassword));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "redirect:/";
    }
}
