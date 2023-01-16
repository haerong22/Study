package com.example.loan.service;

import com.example.loan.domain.Application;
import com.example.loan.dto.ApplicationDto;
import com.example.loan.repository.ApplicationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApplicationServiceTest {

    @InjectMocks
    ApplicationServiceImpl applicationService;

    @Mock
    private ApplicationRepository applicationRepository;

    @Spy
    private ModelMapper modelMapper;

    @Test
    void Should_ReturnResponseOfNewApplicationEntity_When_RequestCreateApplication() {
        Application entity = Application.builder()
                .name("kim")
                .cellPhone("010-1234-1234")
                .email("email@email.com")
                .hopeAmount(BigDecimal.valueOf(50000000))
                .build();

        ApplicationDto.Request request = ApplicationDto.Request.builder()
                .name("kim")
                .cellPhone("010-1234-1234")
                .email("email@email.com")
                .hopeAmount(BigDecimal.valueOf(50000000))
                .build();

        when(applicationRepository.save(ArgumentMatchers.any(Application.class))).thenReturn(entity);

        ApplicationDto.Response actual = applicationService.create(request);

        assertThat(actual.getHopeAmount()).isSameAs(entity.getHopeAmount());
        assertThat(actual.getName()).isSameAs(entity.getName());
        assertThat(actual.getCellPhone()).isSameAs(entity.getCellPhone());
        assertThat(actual.getEmail()).isSameAs(entity.getEmail());
    }
}