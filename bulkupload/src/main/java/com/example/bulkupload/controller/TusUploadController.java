package com.example.bulkupload.controller;

import com.example.bulkupload.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TusUploadController {

    private final FileUploadService fileUploadService;

    @CrossOrigin(origins = "*")
    @RequestMapping(value = {"/api/tus/file/upload", "/api/tus/file/upload/**"})
    public ResponseEntity uploadWithTus(HttpServletRequest request, HttpServletResponse response) {
        // Process a tus upload request
        fileUploadService.process(request, response);

        // Generate HTTP Response Headers
        return httpOkStatus();
    }

    private static ResponseEntity<Object> httpOkStatus() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "*");

        return ResponseEntity.status(HttpStatus.OK)
                .headers(headers)
                .build();
    }
}