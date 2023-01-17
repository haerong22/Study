package com.example.loan.service;

import com.example.loan.dto.ApplicationDto;

public interface ApplicationService {

    ApplicationDto.Response create(ApplicationDto.Request request);

    ApplicationDto.Response get(Long applicationId);

    ApplicationDto.Response update(Long applicationId, ApplicationDto.Request request);

    void delete(Long applicationId);

    Boolean acceptTerms(Long applicationId, ApplicationDto.AcceptTerms request);
}
