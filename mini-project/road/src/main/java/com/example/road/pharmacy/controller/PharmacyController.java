package com.example.road.pharmacy.controller;

import com.example.road.pharmacy.cache.PharmacyRedisTemplateService;
import com.example.road.pharmacy.dto.PharmacyDto;
import com.example.road.pharmacy.service.PharmacyRepositoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PharmacyController {

    private final PharmacyRepositoryService pharmacyRepositoryService;
    private final PharmacyRedisTemplateService pharmacyRedisTemplateService;

    @GetMapping("/redis/save")
    public String save() {

        List<PharmacyDto> pharmacyDtoList = pharmacyRepositoryService.findAll()
                .stream()
                .map(pharmacy -> PharmacyDto.builder()
                        .id(pharmacy.getId())
                        .pharmacyName(pharmacy.getPharmacyName())
                        .pharmacyAddress(pharmacy.getPharmacyAddress())
                        .latitude(pharmacy.getLatitude())
                        .longitude(pharmacy.getLongitude())
                        .build()
                )
                .collect(Collectors.toList());

        pharmacyDtoList.forEach(pharmacyRedisTemplateService::save);

        return "success";
    }
}
