package com.example.bulkupload.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

@Slf4j
@RequiredArgsConstructor
@Component
public class S3Uploader {

    private final AmazonS3Client amazonS3Client;

    @Value("${aws.s3.bucket}")
    private String bucket;

    @Value("${aws.s3.bucket.prefix}")
    private String bucketPrefix;

    @Value("${aws.cloudfront.prefix}")
    private String cloudFrontPrefix;

    @Value("${aws.s3.base.path}")
    private String basePath;

    @Value("${aws.s3.vod.prefix}")
    private String vodPrefix;
    @Value("${video.path}")
    private String videoPath;

    public String upload(String path) {
        File file = new File(videoPath + "/" + path);
        return putS3(file, basePath + vodPrefix + "/" + path);
    }

    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString()
                .replace(bucketPrefix + "/" + basePath, cloudFrontPrefix);
    }

    private void removeFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("S3Uploader - File delete success");
            return;
        }
        log.info("S3Uploader - File delete fail");
    }

}
