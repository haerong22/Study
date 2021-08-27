package com.example.exceldownload.controller;

import com.example.exceldownload.service.ExcelService;
import com.example.exceldownload.util.ExcelXlsxView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ExcelController {

    private final ExcelService excelService;

    @GetMapping("/excel/download")
    public ModelAndView excelDownload(HttpServletRequest request) {
        Map<String, Object> excelData = excelService.excelDownload(request);
        return new ModelAndView(new ExcelXlsxView(), excelData);
    }
}
