package com.example.loan.controller;

import com.example.loan.dto.ApplicationDto;
import com.example.loan.dto.ResponseDto;
import com.example.loan.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/applications")
public class ApplicationController extends AbstractController {

    private final ApplicationService applicationService;

    @PostMapping
    public ResponseDto<ApplicationDto.Response> create(
            @RequestBody ApplicationDto.Request request
    ) {
        return ok(applicationService.create(request));
    }
}
