package com.example.restcontroller.extra.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NaverTranslateResponseMessageResult {

    private String srcLangType;
    private String tarLangType;
    private String translatedText;
}
