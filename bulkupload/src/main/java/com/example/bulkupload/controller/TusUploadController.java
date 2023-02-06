package com.example.bulkupload.controller;

import com.example.bulkupload.dto.UploadResponse;
import com.example.bulkupload.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TusUploadController {

    private final FileUploadService fileUploadService;

    @Value("${video.path}")
    private String savedPath;

    @CrossOrigin(origins = "*")
    @RequestMapping(value = {"/upload", "/upload/**"})
    public ResponseEntity<UploadResponse> uploadWithTus2(HttpServletRequest request, HttpServletResponse response) {
        UploadResponse res = fileUploadService.process(request, response);
        return httpOkStatus(res);
    }

    private static ResponseEntity<UploadResponse> httpOkStatus(UploadResponse res) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "*");

        return ResponseEntity.status(HttpStatus.OK)
                .headers(headers)
                .body(res);
    }

    @GetMapping("/video/{date}/{url}")
    public ResponseEntity<ResourceRegion> videoResource(@RequestHeader HttpHeaders headers,
                                                        @PathVariable String date,
                                                        @PathVariable String url) throws IOException {
        String path = savedPath + "/" + date + "/" + url;
        Resource resource = new FileSystemResource(path);

        long chunkSize = 1024 * 1024;
        long contentLength = resource.contentLength();

        ResourceRegion region;

        try {
            HttpRange httpRange = headers.getRange().stream().findFirst().get();
            long start = httpRange.getRangeStart(contentLength);
            long end = httpRange.getRangeEnd(contentLength);
            long rangeLength = Long.min(chunkSize, end -start + 1);

            region = new ResourceRegion(resource, start, rangeLength);
        } catch (Exception e) {
            long rangeLength = Long.min(chunkSize, contentLength);
            region = new ResourceRegion(resource, 0, rangeLength);
        }

        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                .cacheControl(CacheControl.maxAge(10, TimeUnit.MINUTES))
                .contentType(MediaTypeFactory.getMediaType(resource).orElse(MediaType.APPLICATION_OCTET_STREAM))
                .header("Accept-Ranges", "bytes")
                .eTag(path)
                .body(region);
    }

}