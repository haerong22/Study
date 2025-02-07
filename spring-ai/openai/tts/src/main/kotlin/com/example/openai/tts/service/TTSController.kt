package com.example.openai.tts.service

import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody


@RestController
class TTSController(
    private val ttsService: TTSService,
) {

    @PostMapping("/upload")
    fun textToSpeech(
        @RequestParam("file") file: MultipartFile
    ): ResponseEntity<StreamingResponseBody> {
        val stream = ttsService.textToSpeech(file)
        return ResponseEntity
            .ok()
            .header(HttpHeaders.CONTENT_TYPE, "audio/mpeg") // MP3 파일로 설정
            .body(stream) // byte[]/ byte[], byte[]
    }
}