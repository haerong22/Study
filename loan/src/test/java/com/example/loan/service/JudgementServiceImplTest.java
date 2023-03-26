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

        Assertions.assertThat(actual.getName()).isSameAs(judgement.getName());
        Assertions.assertThat(actual.getApplicationId()).isSameAs(judgement.getApplicationId());
        Assertions.assertThat(actual.getApprovalAmount()).isSameAs(judgement.getApprovalAmount());
    }
}