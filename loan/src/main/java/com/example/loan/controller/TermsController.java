package com.example.loan.controller;

import com.example.loan.dto.ResponseDto;
import com.example.loan.dto.TermsDto;
import com.example.loan.service.TermsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/terms")
public class TermsController extends AbstractController {

    private final TermsService termsService;

    @PostMapping
    public ResponseDto<TermsDto.Response> create(
            @RequestBody TermsDto.Request request
    ) {
        return ok(termsService.create(request));
    }

    @GetMapping
    public ResponseDto<List<TermsDto.Response>> getAll() {
        return ok(termsService.getAll());
    }
}
