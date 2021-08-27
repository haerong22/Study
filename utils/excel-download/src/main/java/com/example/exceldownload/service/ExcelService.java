package com.example.exceldownload.service;

import com.example.exceldownload.dto.BackgroundMusic;
import com.example.exceldownload.util.ExcelWriter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class ExcelService {
    public Map<String, Object> excelDownload(HttpServletRequest request) {

        // 데이터 가져오기
        String uri = "https://acnhapi.com/v1a/backgroundmusic";
        RestTemplate restTemplate = new RestTemplate();
        BackgroundMusic[] result = restTemplate.getForObject(uri, BackgroundMusic[].class);
        List<BackgroundMusic> backgroundMusics = Arrays.asList(result);

        // 엑셀 데이터 생성
        String filename = ExcelWriter.createFileName(BackgroundMusic.class);
        List<List<String>> bodyData = ExcelWriter.createBodyData(backgroundMusics);
        List<String> headData = ExcelWriter.createHeaderName(BackgroundMusic.class);

        Map<String, Object> excelData = new HashMap<>();
        excelData.put("filename", filename);
        excelData.put("head", headData);
        excelData.put("body", bodyData);

        return excelData;
    }
}
