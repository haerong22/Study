package com.monolithicdemo.service;

import com.monolithicdemo.model.entity.Writer;
import com.monolithicdemo.model.form.RegisterWriterForm;
import com.monolithicdemo.repository.WriterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * writer 에 대한 CRUD
 */
@Service
@RequiredArgsConstructor
public class WriterService {

    private final WriterRepository writerRepository;

    public Long registerWriter(RegisterWriterForm registerWriterForm) {

        return writerRepository.save(
                Writer.builder()
                        .name(registerWriterForm.getName())
                        .createdAt(LocalDateTime.now()).build()
        ).getId();
    }
}
