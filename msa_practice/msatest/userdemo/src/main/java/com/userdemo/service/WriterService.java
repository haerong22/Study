package com.userdemo.service;


import com.userdemo.model.entity.Writer;
import com.userdemo.model.entity.repository.WriterRepository;
import com.userdemo.model.form.RegisterWriterForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
/**
 * writer에 대한 CRUD
 */
@RequiredArgsConstructor
public class WriterService {

    private final WriterRepository writerRepository;

    public Long registerWriter(RegisterWriterForm registerWriterForm) {

        return writerRepository.save(
                Writer.builder()
                        .name(registerWriterForm.getName())
                        .createdAt(LocalDateTime.now()).build()
        ).getWriterId();

    }
}
