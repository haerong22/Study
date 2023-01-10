package com.example.loan.service;

import com.example.loan.dto.CounselDto;

public interface CounselService {

    CounselDto.Response create(CounselDto.Request request);

    CounselDto.Response get(Long counselId);
}
