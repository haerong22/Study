package com.example.restcontroller.extra.controller;

import com.example.restcontroller.common.model.ResponseResult;
import com.example.restcontroller.extra.model.OpenApiResult;
import com.example.restcontroller.extra.model.PharmacySearch;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.Collections;

@RestController
@RequiredArgsConstructor
public class ApiExtraController {

    @GetMapping("/api/extra/pharmacy/string")
    public String chapter4_4() {

        String apiKey = "JCkqoWYEf9zq0vRVR1TA8Nj6QUIl96TFQZwLnzlr32vbYPx7rJaZvCvq9box6J5WwxpNe5NSKoNzYenwdcXQDA%3D%3D";
        String url = "http://apis.data.go.kr/B552657/ErmctInsttInfoInqireService/getParmacyListInfoInqire?serviceKey=%s&pageNo=1&numOfRows=10";

        String apiResult = "";

        try {
            URI uri = new URI(String.format(url, apiKey));
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            String result = restTemplate.getForObject(uri, String.class);

            apiResult = result;
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return apiResult;
    }

    @GetMapping("/api/extra/pharmacy/json")
    public ResponseEntity<?> chapter4_5() {

        String apiKey = "JCkqoWYEf9zq0vRVR1TA8Nj6QUIl96TFQZwLnzlr32vbYPx7rJaZvCvq9box6J5WwxpNe5NSKoNzYenwdcXQDA%3D%3D";
        String url = "http://apis.data.go.kr/B552657/ErmctInsttInfoInqireService/getParmacyListInfoInqire?serviceKey=%s&pageNo=1&numOfRows=10";

        String apiResult = "";

        try {
            URI uri = new URI(String.format(url, apiKey));
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            String result = restTemplate.getForObject(uri, String.class);

            apiResult = result;
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        OpenApiResult jsonResult = null;

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            jsonResult = objectMapper.readValue(apiResult, OpenApiResult.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return ResponseResult.success(jsonResult);
    }

    @GetMapping("/api/extra/pharmacy")
    public ResponseEntity<?> chapter4_6(@RequestBody PharmacySearch pharmacySearch) {

        String apiKey = "JCkqoWYEf9zq0vRVR1TA8Nj6QUIl96TFQZwLnzlr32vbYPx7rJaZvCvq9box6J5WwxpNe5NSKoNzYenwdcXQDA%3D%3D";
        String url =String.format("http://apis.data.go.kr/B552657/ErmctInsttInfoInqireService/getParmacyListInfoInqire?serviceKey=%s&pageNo=1&numOfRows=10", apiKey);

        String apiResult = "";

        try {
            url += String.format("&Q0=%s&Q1=%s",
                    URLEncoder.encode(pharmacySearch.getSearchSido(), "utf-8"),
                    URLEncoder.encode(pharmacySearch.getSearchGugun(), "utf-8"));

            URI uri = new URI(url);
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            String result = restTemplate.getForObject(uri, String.class);

            apiResult = result;
        } catch (URISyntaxException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        OpenApiResult jsonResult = null;

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            jsonResult = objectMapper.readValue(apiResult, OpenApiResult.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return ResponseResult.success(jsonResult);
    }
}
