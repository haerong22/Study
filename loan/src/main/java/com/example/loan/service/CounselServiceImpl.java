package com.example.loan.service;

import com.example.loan.domain.Counsel;
import com.example.loan.dto.CounselDto;
import com.example.loan.repository.CounselRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CounselServiceImpl implements CounselService {

    private final CounselRepository counselRepository;

    private final ModelMapper modelMapper;

    @Override
    public CounselDto.Response create(CounselDto.Request request) {
        Counsel counsel = modelMapper.map(request, Counsel.class);
        counsel.setAppliedAt(LocalDateTime.now());

        Counsel created = counselRepository.save(counsel);
        return modelMapper.map(created, CounselDto.Response.class);
    }
}
