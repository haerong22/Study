package com.example.loan.controller;

import com.example.loan.dto.CounselDto;
import com.example.loan.dto.ResponseDto;
import com.example.loan.service.CounselService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/counsels")
public class CounselController extends AbstractController {

    private final CounselService counselService;

    @PostMapping
    public ResponseDto<CounselDto.Response> create(@RequestBody CounselDto.Request request) {
        return ok(counselService.create(request));
    }

}
