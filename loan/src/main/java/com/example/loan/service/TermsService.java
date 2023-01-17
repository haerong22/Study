package com.example.loan.service;

import com.example.loan.dto.TermsDto;

import java.util.List;

public interface TermsService {

    TermsDto.Response create(TermsDto.Request request);

    List<TermsDto.Response> getAll();
}
