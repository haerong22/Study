package com.example.loan.service;

import com.example.loan.domain.Terms;
import com.example.loan.dto.TermsDto;
import com.example.loan.repository.TermsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.InstanceOfAssertFactories.list;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TermsServiceTest {

    @InjectMocks
    TermsServiceImpl termsService;

    @Mock
    private TermsRepository termsRepository;

    @Spy
    private ModelMapper modelMapper;

    @Test
    void Should_ReturnResponseOfNewTermsEntity_When_RequestTerms() {
        Terms entity = Terms.builder()
                .name("대출 이용 약관")
                .termsDetailUrl("https://terms.com/20230117")
                .build();

        TermsDto.Request request = TermsDto.Request.builder()
                .name("대출 이용 약관")
                .termsDetailUrl("https://terms.com/20230117")
                .build();

        when(termsRepository.save(ArgumentMatchers.any(Terms.class))).thenReturn(entity);

        TermsDto.Response actual = termsService.create(request);

        assertThat(actual.getName()).isSameAs(entity.getName());
        assertThat(actual.getTermsDetailUrl()).isSameAs(entity.getTermsDetailUrl());

    }

    @Test
    void Should_ReturnAllResponseOfExistTermsEntities_When_RequestTermsList() {
        Terms entityA = Terms.builder()
                .name("대출 이용약관 1")
                .termsDetailUrl("https://terms.com/loan")
                .build();

        Terms entityB = Terms.builder()
                .name("대출 이용약관 2")
                .termsDetailUrl("https://terms.com/loan2")
                .build();

        List<Terms> list = Arrays.asList(entityA, entityB);

        when(termsRepository.findAll()).thenReturn(list);

        List<TermsDto.Response> actual = termsService.getAll();

        assertThat(actual.size()).isSameAs(list.size());
    }
}