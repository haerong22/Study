package com.example.road.direction.controller;

import com.example.road.direction.entity.Direction;
import com.example.road.direction.service.DirectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Controller
@RequiredArgsConstructor
public class DirectionController {

    private final DirectionService directionService;
    private static final String DIRECTION_BASE_URL = "https://map.kakao.com/link/map/";

    @GetMapping("/dir/{encodedId}")
    public String searchDirection(@PathVariable("encodedId") String encodedId) {
        Direction resultDirection = directionService.findById(encodedId);

        String directionParams = String.join(
                ",",
                resultDirection.getTargetPharmacyName(),
                String.valueOf(resultDirection.getTargetLatitude()),
                String.valueOf(resultDirection.getTargetLongitude())
        );

        String directionUrl = UriComponentsBuilder
                .fromHttpUrl(DIRECTION_BASE_URL + directionParams)
                .toUriString();

        return "redirect:" + directionUrl;
    }
}
