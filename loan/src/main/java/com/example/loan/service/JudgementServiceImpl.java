package com.example.loan.service;

import com.example.loan.domain.Judgement;
import com.example.loan.dto.JudgementDto;
import com.example.loan.exception.BaseException;
import com.example.loan.exception.ResultType;
import com.example.loan.repository.ApplicationRepository;
import com.example.loan.repository.JudgementRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JudgementServiceImpl implements JudgementService {

    private final JudgementRepository judgementRepository;
    private final ApplicationRepository applicationRepository;
    private final ModelMapper modelMapper;

    @Override
    public JudgementDto.Response create(JudgementDto.Request request) {
        // 신청 정보 검증
        Long applicationId = request.getApplicationId();
        if (!isPresentApplication(applicationId)) {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        }

        // request dto -> entity -> save
        Judgement judgement = modelMapper.map(request, Judgement.class);

        Judgement saved = judgementRepository.save(judgement);

        // save -> response dto
        return modelMapper.map(saved, JudgementDto.Response.class);
    }

    @Override
    public JudgementDto.Response get(Long judgementId) {
        Judgement judgement = judgementRepository.findById(judgementId)
                .orElseThrow(() -> new BaseException(ResultType.SYSTEM_ERROR));

        return modelMapper.map(judgement, JudgementDto.Response.class);
    }

    @Override
    public JudgementDto.Response getJudgementOfApplication(Long applicationId) {
        if (!isPresentApplication(applicationId)) {
            throw new BaseException(ResultType.SYSTEM_ERROR);
        }

        Judgement judgement = judgementRepository.findByApplicationId(applicationId)
                .orElseThrow(() -> new BaseException(ResultType.SYSTEM_ERROR));

        return modelMapper.map(judgement, JudgementDto.Response.class);
    }

    @Override
    public JudgementDto.Response update(Long judgementId, JudgementDto.Request request) {
        Judgement judgement = judgementRepository.findById(judgementId)
                .orElseThrow(() -> new BaseException(ResultType.SYSTEM_ERROR));

        judgement.setName(request.getName());
        judgement.setApprovalAmount(request.getApprovalAmount());

        judgementRepository.save(judgement);

        return modelMapper.map(judgement, JudgementDto.Response.class);
    }

    private boolean isPresentApplication(Long applicationId) {
        return applicationRepository.findById(applicationId).isPresent();
    }
}
