package com.example.road.api.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
@Service
public class KakaoUriBuilderService {

    private static final String KAKAO_LOCAL_SEARCH_ADDRESS_URL = "https://dapi.kakao.com/v2/local/search/address.json";
    private static final String KAKAO_LOCAL_SEARCH_CATEGORY_URL = "https://dapi.kakao.com/v2/local/search/category.json";

    public URI buildUriByAddressSearch(String address) {

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(KAKAO_LOCAL_SEARCH_ADDRESS_URL);
        uriBuilder.queryParam("query", address);

        URI uri = uriBuilder.build().encode().toUri();
        log.info("[KakaoUriBuilderService builderUriByAddressSearch] address: {}, uri: {}", address, uri);

        return uri;
    }

    public URI buildUriByCategorySearch(double latitude, double longitude, double radius, String category) {

        double meterRadius = radius * 1000;

        UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromHttpUrl(KAKAO_LOCAL_SEARCH_CATEGORY_URL);
        urlBuilder.queryParam("category_group_code", category);
        urlBuilder.queryParam("x", longitude);
        urlBuilder.queryParam("y", latitude);
        urlBuilder.queryParam("radius", meterRadius);
        urlBuilder.queryParam("sort", "distance");

        URI uri = urlBuilder.build().encode().toUri();

        log.info("[KakaoUriBuilderService buildUriByCategorySearch] uri: {}", uri);

        return uri;
    }
}
