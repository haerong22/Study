package com.spring.security1.config.oauth;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    // 구글로부터 받은 userRequest 데이터에 대한 후처리 되는 함수
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("userRequest: "+ userRequest.getClientRegistration()); // registrationId로 어떤oauth로 로그인했는지 확인가능.
        System.out.println("userRequest: "+ userRequest.getAccessToken().getTokenValue());
        // 구글 로그인 버튼 클릭 -> 구글 로그인 창 -> 로그인을 완료 -> code를 리턴(OAuth-Client 라이브러리) -> AccessToken 요청
        // userRequest 정보 -> loadUser함수 -> 구글로 부터 회원 프로필 받아준다.
        System.out.println("userRequest: "+ super.loadUser(userRequest).getAttributes());

        OAuth2User auth2User = super.loadUser(userRequest);
        return super.loadUser(userRequest);
    }
}
