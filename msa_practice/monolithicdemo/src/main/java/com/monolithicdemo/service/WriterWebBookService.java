package com.monolithicdemo.service;

import com.monolithicdemo.model.entity.WebBook;
import com.monolithicdemo.model.form.RegisterWebBookForm;
import com.monolithicdemo.repository.WebBookRepository;
import com.monolithicdemo.repository.WriterRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class WriterWebBookService {

    private final WriterRepository writerRepository;
    private final WebBookRepository webBookRepository;

    public Long registerWebBook(Long writerId, RegisterWebBookForm registerWebBookForm) {

        if (writerRepository.existsById(writerId)) {
            return webBookRepository.save(
                    WebBook.builder()
                            .name(registerWebBookForm.getName())
                            .description(registerWebBookForm.getDescription())
                            .createdAt(LocalDateTime.now())
                            .build())
                    .getWebBookId();
        } else {
            return null;
        }
    }
}
