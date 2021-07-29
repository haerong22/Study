package com.monolithicdemo.controller;

import com.monolithicdemo.model.form.RegisterWriterForm;
import com.monolithicdemo.service.WriterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/writer")
@RequiredArgsConstructor
public class WriterController {

    private final WriterService writerService;

    @PostMapping("/")
    public ResponseEntity<Long> registerWriter(@RequestBody RegisterWriterForm registerWriterForm) {
        return ResponseEntity.ok().body(writerService.registerWriter(registerWriterForm));
    }
}
