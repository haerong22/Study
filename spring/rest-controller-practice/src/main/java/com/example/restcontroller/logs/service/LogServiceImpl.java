package com.example.restcontroller.logs.service;

import com.example.restcontroller.logs.entity.Logs;
import com.example.restcontroller.logs.repository.LogsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
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

    @Transactional
    @Override
    public void deleteLog() {
        logsRepository.deleteAll();
    }
}
