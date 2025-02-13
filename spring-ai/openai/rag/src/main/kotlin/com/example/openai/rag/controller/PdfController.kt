package com.example.openai.rag.controller

import com.example.openai.rag.service.PdfService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class PdfController(
    private val pdfService: PdfService,
) {

    @GetMapping("/api/answer")
    fun simplify(question: String): String {
        return pdfService.simplify(question)
    }
}