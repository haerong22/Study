package com.monolithicdemo.service;

import com.monolithicdemo.model.entity.Reader;
import com.monolithicdemo.model.form.RegisterReaderForm;
import com.monolithicdemo.repository.ReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
/**
 * Reader에 대한 CRUD
 */
@RequiredArgsConstructor
public class ReaderService {

    private final ReaderRepository ReaderRepository;

    public Long registerReader(RegisterReaderForm registerReaderForm) {

        return ReaderRepository.save(
                Reader.builder()
                        .name(registerReaderForm.getName())
                        .createdAt(LocalDateTime.now()).build()
        ).getReaderId();

    }
}
