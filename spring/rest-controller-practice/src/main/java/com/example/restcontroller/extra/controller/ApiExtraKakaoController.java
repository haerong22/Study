package com.example.restcontroller.extra.controller;

import com.example.restcontroller.common.model.ResponseResult;
import com.example.restcontroller.extra.model.KakaoTranslateInput;
import com.example.restcontroller.extra.model.KakaoTranslateResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class ApiExtraKakaoController {

    @GetMapping("/api/extra/kakao/translate")
    public ResponseEntity<?> chapter4_8(@RequestBody KakaoTranslateInput kakaoTranslateInput) {

        String restApikey = "b358d5ce7911b4a65362196959c40a93";
        String url = "https://dapi.kakao.com/v2/translation/translate";

        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("src_lang", "kr");
        parameters.add("target_lang", "en");
        parameters.add("query", kakaoTranslateInput.getText());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Authorization", "KakaoAK " + restApikey);

        HttpEntity<?> formEntity = new HttpEntity<>(parameters, headers);

        KakaoTranslateResponse kakaoTranslateResponse = restTemplate.postForObject(url, formEntity, KakaoTranslateResponse.class);
        return ResponseResult.success(kakaoTranslateResponse);
    }
}
