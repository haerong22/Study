package com.webbookdemo.service;

import com.webbookdemo.model.entity.WebBook;
import com.webbookdemo.model.entity.repository.WebBookRepository;
import com.webbookdemo.model.form.WebBookRegisterForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class WebBookService {

    private final WebBookRepository webBookRepository;

    public Long addWebBook(WebBookRegisterForm webBookRegisterForm) {

        return webBookRepository.save(
                WebBook.builder()
                        .writerId(webBookRegisterForm.getWriterId())
                        .name(webBookRegisterForm.getName())
                        .description(webBookRegisterForm.getDescription())
                        .createdAt(LocalDateTime.now())
                        .build()
        ).getWebBookId();
    }
}
