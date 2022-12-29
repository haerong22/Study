package com.example.bulkupload.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.desair.tus.server.TusFileUploadService;
import me.desair.tus.server.exception.TusException;
import me.desair.tus.server.upload.UploadInfo;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileUploadService {

    private final TusFileUploadService tusFileUploadService;

    @Value("${video.path}")
    private String savedPath;

    public String process(HttpServletRequest request, HttpServletResponse response) {
        try {
            tusFileUploadService.process(request, response);

            UploadInfo uploadInfo = tusFileUploadService.getUploadInfo(request.getRequestURI());

            if (uploadInfo != null && !uploadInfo.isUploadInProgress()) {
                String filename = createFile(tusFileUploadService.getUploadedBytes(request.getRequestURI()), uploadInfo.getFileName());

                tusFileUploadService.deleteUpload(request.getRequestURI());

                return filename;
            }

            return null;
        } catch (IOException | TusException e) {
            log.error("exception was occurred. message={}", e.getMessage(), e);

            throw new RuntimeException(e);
        }
    }

    private String createFile(InputStream is, String filename) throws IOException {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 12);

        String[] split = filename.split("\\.");

        String savedFilename = uuid + "." + split[split.length - 1];

        LocalDate today = LocalDate.now();
        File file = new File(savedPath + "/" + today, savedFilename);

        FileUtils.copyInputStreamToFile(is, file);

        return today + "/" + savedFilename;
    }
}