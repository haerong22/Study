package com.example.openai.math.controller

import com.example.openai.math.entity.ImageAnalysisVO
import com.example.openai.math.service.ImageAnalysisService
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

@RestController
@RequestMapping("/image-text")
class ImageAnalysisController(
    @Value("\${upload.path}")
    private val uploadPath: String,
    private val imageAnalysisService: ImageAnalysisService,
) {

    @PostMapping("/analyze")
    @Throws(IOException::class)
    fun getMultimodalResponse(
        @RequestParam("image") imageFile: MultipartFile,
        @RequestParam(defaultValue = "이 이미지에 무엇이 있나요?") message: String
    ): ResponseEntity<ImageAnalysisVO> {
        val uploadDirectory = File(uploadPath)
        if (!uploadDirectory.exists()) {
            uploadDirectory.mkdirs()
        }

        val filename = imageFile.originalFilename
        val filePath = Paths.get(uploadPath, filename)
        Files.write(filePath, imageFile.bytes)

        val analysisText: String = imageAnalysisService.analyzeImage(imageFile, message)
        val imageUrl = "/uploads/$filename"

        val response = ImageAnalysisVO(imageUrl, analysisText)
        return ResponseEntity.ok(response)
    }
}