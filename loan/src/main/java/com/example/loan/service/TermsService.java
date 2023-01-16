package com.example.loan.service;

import com.example.loan.dto.TermsDto;

public interface TermsService {

    TermsDto.Response create(TermsDto.Request request);
}
