package com.example.loan.service;

import com.example.loan.domain.Terms;
import com.example.loan.dto.TermsDto;
import com.example.loan.repository.TermsRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TermsServiceImpl implements TermsService {

    private final TermsRepository termsRepository;
    private final ModelMapper modelMapper;

    @Override
    public TermsDto.Response create(TermsDto.Request request) {
        Terms terms = modelMapper.map(request, Terms.class);

        Terms created = termsRepository.save(terms);

        return modelMapper.map(created, TermsDto.Response.class);
    }
}
