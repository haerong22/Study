package com.example.restcontroller.logs.service;

import com.example.restcontroller.logs.entity.Logs;
import com.example.restcontroller.logs.repository.LogsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LogServiceImpl implements LogService{

    private final LogsRepository logsRepository;

    @Override
    public void add(String text) {

        logsRepository.save(Logs.builder()
                .text(text)
                .regDate(LocalDateTime.now())
                .build());
    }
}
