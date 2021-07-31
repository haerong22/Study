package com.userdemo.service;

import com.userdemo.model.entity.Reader;
import com.userdemo.model.entity.repository.ReaderRepository;
import com.userdemo.model.form.RegisterReaderForm;
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
