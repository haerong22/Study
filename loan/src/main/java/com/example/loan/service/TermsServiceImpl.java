package com.example.loan.service;

import com.example.loan.domain.Terms;
import com.example.loan.dto.TermsDto;
import com.example.loan.repository.TermsRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public List<TermsDto.Response> getAll() {
        List<Terms> termsList = termsRepository.findAll();

        return termsList.stream().map(t -> modelMapper.map(t, TermsDto.Response.class)).collect(Collectors.toList());
    }
}
