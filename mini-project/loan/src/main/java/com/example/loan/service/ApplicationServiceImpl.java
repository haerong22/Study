package com.example.loan.service;

import com.example.loan.domain.AcceptTerms;
import com.example.loan.domain.Application;
import com.example.loan.domain.Terms;
import com.example.loan.dto.ApplicationDto;
import com.example.loan.exception.BaseException;
import com.example.loan.exception.ResultType;
import com.example.loan.repository.AcceptTermsRepository;
import com.example.loan.repository.ApplicationRepository;
import com.example.loan.repository.TermsRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final TermsRepository termsRepository;
    private final AcceptTermsRepository acceptTermsRepository;
    private final ModelMapper modelMapper;

    @Override
    public ApplicationDto.Response create(ApplicationDto.Request request) {
        Application application = modelMapper.map(request, Application.class);
        application.setAppliedAt(LocalDateTime.now());

        Application applied = applicationRepository.save(application);

        return modelMapper.map(applied, ApplicationDto.Response.class);
    }

    @Override
    public ApplicationDto.Response get(Long applicationId) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new BaseException(ResultType.SYSTEM_ERROR));

        return modelMapper.map(application, ApplicationDto.Response.class);
    }

    @Override
    public ApplicationDto.Response update(Long applicationId, ApplicationDto.Request request) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new BaseException(ResultType.SYSTEM_ERROR));

        application.setName(request.getName());
        application.setCellPhone(request.getCellPhone());
        application.setEmail(request.getEmail());
        application.setHopeAmount(request.getHopeAmount());

        applicationRepository.save(application);

        return modelMapper.map(application, ApplicationDto.Response.class);
    }

    @Override
    public void delete(Long applicationId) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new BaseException(ResultType.SYSTEM_ERROR));

        application.setIsDeleted(true);

        applicationRepository.save(application);
    }

    @Override
    public Boolean acceptTerms(Long applicationId, ApplicationDto.AcceptTerms request) {
        applicationRepository.findById(applicationId)
                .orElseThrow(() -> new BaseException(ResultType.SYSTEM_ERROR));


        List<Terms> termsList = termsRepository.findAll(Sort.by(Sort.Direction.ASC, "termsId"));

        if (termsList.isEmpty()) {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        }

        List<Long> acceptTermsIds = request.getAcceptTermsIds();

        if (termsList.size() != acceptTermsIds.size()) {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        }

        List<Long> termsIds = termsList.stream()
                .map(Terms::getTermsId)
                .collect(Collectors.toList());

        Collections.sort(acceptTermsIds);

        if (!new HashSet<>(termsIds).containsAll(acceptTermsIds)) {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        }

        for (Long termsId : acceptTermsIds) {
            acceptTermsRepository.save(
                    AcceptTerms.builder()
                            .termsId(termsId)
                            .applicationId(applicationId)
                            .build()
            );
        }

        return true;
    }
}
