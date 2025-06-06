package com.example.loan.controller;

import com.example.loan.dto.ApplicationDto;
import com.example.loan.dto.ResponseDto;
import com.example.loan.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{applicationId}")
    public ResponseDto<ApplicationDto.Response> get(
            @PathVariable Long applicationId
    ) {
        return ok(applicationService.get(applicationId));
    }

    @PutMapping("/{applicationId}")
    public ResponseDto<ApplicationDto.Response> update(
            @PathVariable Long applicationId,
            @RequestBody ApplicationDto.Request request
    ) {
        return ok(applicationService.update(applicationId, request));
    }

    @DeleteMapping("/{applicationId}")
    public ResponseDto<Void> delete(
            @PathVariable Long applicationId
    ) {
        applicationService.delete(applicationId);
        return ok();
    }

    @PostMapping("/{applicationId}/terms")
    public ResponseDto<Boolean> acceptTerms(
            @PathVariable Long applicationId,
            @RequestBody ApplicationDto.AcceptTerms request
    ) {
        return ok(applicationService.acceptTerms(applicationId, request));
    }
}
