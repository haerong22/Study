package com.example.bulkupload.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.desair.tus.server.TusFileUploadService;
import me.desair.tus.server.exception.TusException;
import me.desair.tus.server.upload.UploadInfo;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileUploadService {

    private final TusFileUploadService tusFileUploadService;

    public void process(HttpServletRequest request, HttpServletResponse response) {
        try {
            // Process a tus upload request
            tusFileUploadService.process(request, response);

            // Get upload information
            UploadInfo uploadInfo = tusFileUploadService.getUploadInfo(request.getRequestURI());

            if (uploadInfo != null && !uploadInfo.isUploadInProgress()) {
                // Progress status is successful: Create file
                createFile(tusFileUploadService.getUploadedBytes(request.getRequestURI()), uploadInfo.getFileName());

                // Delete an upload associated with the given upload url
                tusFileUploadService.deleteUpload(request.getRequestURI());
            }
        } catch (IOException | TusException e) {
            log.error("exception was occurred. message={}", e.getMessage(), e);

            throw new RuntimeException(e);
        }
    }

    private void createFile(InputStream is, String filename) throws IOException {
        File file = new File("dest/", filename);

        FileUtils.copyInputStreamToFile(is, file);
    }
}