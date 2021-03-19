package com.example.restcontroller.extra.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OpenApiResultResponse {

    private OpenApiResultResponseHeader header;
    private OpenApiResultResponseBody body;
}
