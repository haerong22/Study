package com.example.loan.controller;

import com.example.loan.dto.JudgementDto;
import com.example.loan.dto.ResponseDto;
import com.example.loan.service.JudgementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/judgements")
public class JudgementController extends AbstractController {

    private final JudgementService judgementService;

    @PostMapping
    public ResponseDto<JudgementDto.Response> create(@RequestBody JudgementDto.Request request) {
        return ok(judgementService.create(request));
    }
}
