package com.example.openai.image.controller

import org.springframework.http.ContentDisposition
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate
import java.net.URI

@RestController
class FileDownloadController(
    private val restTemplate: RestTemplate,
) {

    @GetMapping("/download-file")
    fun downloadFile(
        @RequestParam url: String
    ): ResponseEntity<ByteArray> {
        try {
            val uri = URI(url)
            val response: ResponseEntity<ByteArray> = restTemplate.getForEntity(uri, ByteArray::class.java)
            val fileName = extractFileName(url)

            val downloadHeaders = HttpHeaders()
            downloadHeaders.contentType = MediaType.APPLICATION_OCTET_STREAM
            downloadHeaders.contentDisposition = ContentDisposition.attachment().filename(fileName).build()

            return ResponseEntity(response.body, downloadHeaders, HttpStatus.OK)
        } catch (e: Exception) {
            return ResponseEntity.badRequest().body(("Failed to download file: " + e.message).toByteArray())
        }
    }

    private fun extractFileName(url: String): String {
        val path: String = URI.create(url).getPath() // .png?__X
        return path.substring(path.lastIndexOf("/") + 1)
    }
}