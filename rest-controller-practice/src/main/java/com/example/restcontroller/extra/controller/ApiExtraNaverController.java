package com.example.restcontroller.extra.controller;

import com.example.restcontroller.common.model.ResponseResult;
import com.example.restcontroller.extra.model.KakaoTranslateResponse;
import com.example.restcontroller.extra.model.NaverTranslateInput;
import com.example.restcontroller.extra.model.NaverTranslateResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequiredArgsConstructor
public class ApiExtraNaverController {

    @GetMapping("/api/extra/naver/translate")
    public ResponseEntity<?> chapter4_9(@RequestBody NaverTranslateInput naverTranslateInput) {
//        curl "https://openapi.naver.com/v1/papago/n2mt" \
//        -H "Content-Type: application/x-www-form-urlencoded; charset=UTF-8" \
//        -H "X-Naver-Client-Id: {애플리케이션 등록 시 발급받은 클라이언트 아이디 값}" \
//        -H "X-Naver-Client-Secret: {애플리케이션 등록 시 발급받은 클라이언트 시크릿 값}" \
//        -d "source=ko&target=en&text=만나서 반갑습니다." -v

        String clientId = "goQZ03tXlaISGqa0Lgau";
        String clientSecret = "WPVuMeYLHS";
        String url = "https://openapi.naver.com/v1/papago/n2mt";

        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("source", "ko");
        parameters.add("target", "en");
        parameters.add("text", naverTranslateInput.getText());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("X-Naver-Client-Id", clientId);
        headers.add("X-Naver-Client-Secret", clientSecret);

        HttpEntity<?> formEntity = new HttpEntity<>(parameters, headers);

        NaverTranslateResponse naverTranslateResponse = restTemplate.postForObject(url, formEntity, NaverTranslateResponse.class);

        return ResponseResult.success(naverTranslateResponse);
    }
}
