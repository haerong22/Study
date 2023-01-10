package com.example.loan.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.example.loan.domain.Counsel;
import com.example.loan.dto.CounselDto;
import com.example.loan.repository.CounselRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
class CounselServiceTest {

    @InjectMocks
    CounselServiceImpl counselService;

    @Mock
    private CounselRepository counselRepository;

    @Spy
    private ModelMapper modelMapper;

    @Test
    void Should_ReturnResponseOfNewCounselEntity_When_RequestCounsel() {
        Counsel entity = Counsel.builder()
                .name("kim")
                .cellPhone("010-1234-1234")
                .email("email@email.com")
                .memo("대출 해줭")
                .zipCode("12345")
                .address("서울특별시")
                .addressDetail("101동 101호")
                .build();

        CounselDto.Request request = CounselDto.Request.builder()
                .name("kim")
                .cellPhone("010-1234-1234")
                .email("email@email.com")
                .memo("대출 해줭")
                .zipCode("12345")
                .address("서울특별시")
                .addressDetail("101동 101호")
                .build();


        when(counselRepository.save(ArgumentMatchers.any(Counsel.class))).thenReturn(entity);

        CounselDto.Response actual = counselService.create(request);

        assertThat(actual.getName()).isSameAs(entity.getName());
    }
}