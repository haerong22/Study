package com.example.loan.service;

import com.example.loan.dto.ApplicationDto;
import com.example.loan.dto.JudgementDto;

public interface JudgementService {

    JudgementDto.Response create(JudgementDto.Request request);

    JudgementDto.Response get(Long judgementId);

    JudgementDto.Response getJudgementOfApplication(Long applicationId);

    JudgementDto.Response update(Long judgementId, JudgementDto.Request request);

    void delete(Long judgementId);

    ApplicationDto.GrantAmount grant(Long judgementId);
}
