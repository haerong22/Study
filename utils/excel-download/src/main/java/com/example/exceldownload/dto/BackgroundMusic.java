package com.example.exceldownload.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ExcelFileName
public class BackgroundMusic implements ExcelDto{
    @ExcelColumnName(headerName = "music id")
    private int id;

    @ExcelColumnName
    @JsonProperty("file-name")
    private String filename;

    @ExcelColumnName
    private String hour;

    @ExcelColumnName
    private String weather;

    @ExcelColumnName(headerName = "music uri")
    @JsonProperty("music_uri")
    private String musicUri;

    @Override
    public List<String> mapToList() {
        return Arrays.asList(String.valueOf(id), filename, hour, weather, musicUri);
    }
}
