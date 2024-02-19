package com.example.bulkupload.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UploadResponse {

    private String videoUrl;
    private String thumbnailUrl;
}
