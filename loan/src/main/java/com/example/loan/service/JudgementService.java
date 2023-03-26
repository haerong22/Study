package com.example.loan.service;

import com.example.loan.dto.JudgementDto;

public interface JudgementService {

    JudgementDto.Response create(JudgementDto.Request request);
}
