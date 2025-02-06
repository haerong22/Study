package com.example.openai.stt.controller

import com.example.openai.stt.service.SttService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile


@RestController
class STTController(
    private val sttService: SttService,
) {

    @PostMapping("/transcribe")
    fun transcribe(@RequestParam("file") file: MultipartFile): ResponseEntity<String> {
        return ResponseEntity(sttService.transcription(file), HttpStatus.OK)
    }
}