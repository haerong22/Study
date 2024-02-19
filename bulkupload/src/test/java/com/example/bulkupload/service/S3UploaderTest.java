package com.example.bulkupload.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class S3UploaderTest {

    @Autowired
    S3Uploader s3Uploader;

    @Test
    void uploadTest() {

        Long id = 333L;
        String path = "2023-02-06/155e11c4563d.png";

        String upload = s3Uploader.upload(path);

        System.out.println("upload = " + upload);
    }

}