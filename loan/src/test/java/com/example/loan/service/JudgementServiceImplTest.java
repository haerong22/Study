package com.example.loan.service;

import com.example.loan.domain.Application;
import com.example.loan.domain.Judgement;
import com.example.loan.dto.JudgementDto;
import com.example.loan.repository.ApplicationRepository;
import com.example.loan.repository.JudgementRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JudgementServiceImplTest {

    @InjectMocks
    private JudgementServiceImpl judgementService;

    @Mock
    private JudgementRepository judgementRepository;

    @Mock
    private ApplicationRepository applicationRepository;

    @Spy
    private ModelMapper modelMapper;

    @Test
    void Should_ReturnResponseOfNewJudgementEntity_When_RequestNewJudgement() {
        Judgement judgement = Judgement.builder()
                .applicationId(1L)
                .name("member kim")
                .approvalAmount(BigDecimal.valueOf(5000000))
                .build();

        JudgementDto.Request request = JudgementDto.Request.builder()
                .applicationId(1L)
                .name("member kim")
                .approvalAmount(BigDecimal.valueOf(5000000))
                .build();

        when(applicationRepository.findById(1L)).thenReturn(Optional.ofNullable(Application.builder().build()));
        when(judgementRepository.save(ArgumentMatchers.any(Judgement.class))).thenReturn(judgement);

        JudgementDto.Response actual = judgementService.create(request);

        assertThat(actual.getName()).isSameAs(judgement.getName());
        assertThat(actual.getApplicationId()).isSameAs(judgement.getApplicationId());
        assertThat(actual.getApprovalAmount()).isSameAs(judgement.getApprovalAmount());
    }

    @Test
    void Should_ReturnResponseOfExistJudgementEntity_When_RequestExistJudgementId() {

        Judgement entity = Judgement.builder()
                .judgementId(1L)
                .build();

        when(judgementRepository.findById(1L)).thenReturn(Optional.ofNullable(entity));

        JudgementDto.Response actual = judgementService.get(1L);

        assertThat(actual.getJudgementId()).isSameAs(1L);
    }

    @Test
    void Should_ReturnResponseOfExistJudgementEntity_When_RequestExistApplicationId() {

        Judgement judgementEntity
                = Judgement.builder()
                .judgementId(1L)
                .build();

        Application applicationEntity = Application.builder()
                .applicationId(1L)
                .build();

        when(applicationRepository.findById(1L)).thenReturn(Optional.ofNullable(applicationEntity));
        when(judgementRepository.findByApplicationId(1L)).thenReturn(Optional.ofNullable(judgementEntity));

        JudgementDto.Response actual = judgementService.getJudgementOfApplication(1L);

        assertThat(actual.getJudgementId()).isSameAs(1L);
    }

    @Test
    void Should_ReturnUpdatedResponseOfExistJudgementEntity_When_RequestUpdateExistJudgementInfo() {

        Judgement entity = Judgement.builder()
                .judgementId(1L)
                .name("kim")
                .approvalAmount(BigDecimal.valueOf(5000000))
                .build();

        JudgementDto.Request request = JudgementDto.Request.builder()
                .name("lee")
                .approvalAmount(BigDecimal.valueOf(10000000))
                .build();

        when(judgementRepository.findById(1L)).thenReturn(Optional.ofNullable(entity));
        when(judgementRepository.save(ArgumentMatchers.any(Judgement.class))).thenReturn(entity);

        JudgementDto.Response actual = judgementService.update(1L, request);

        assertThat(actual.getJudgementId()).isSameAs(1L);
        assertThat(actual.getName()).isSameAs(request.getName());
        assertThat(actual.getApprovalAmount()).isSameAs(request.getApprovalAmount());
    }
}