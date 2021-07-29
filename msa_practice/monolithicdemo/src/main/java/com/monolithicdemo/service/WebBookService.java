package com.monolithicdemo.service;

import com.monolithicdemo.model.dto.WebBookDto;
import com.monolithicdemo.repository.WebBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WebBookService {

    private final WebBookRepository webBookRepository;

    public List<WebBookDto> getWebBookList() {
        return webBookRepository.findAll().stream()
                .map(webBook -> WebBookDto.from(webBook))
                .collect(Collectors.toList());
    }
}
