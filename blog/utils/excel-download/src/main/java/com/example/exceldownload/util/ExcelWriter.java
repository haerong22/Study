package com.example.exceldownload.util;

import com.example.exceldownload.dto.ExcelColumnName;
import com.example.exceldownload.dto.ExcelDto;
import com.example.exceldownload.dto.ExcelFileName;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelWriter {

    private final Workbook workbook;
    private final Map<String, Object> data;
    private final HttpServletResponse response;

    public ExcelWriter(Workbook workbook, Map<String, Object> data, HttpServletResponse response) {
        this.workbook = workbook;
        this.data = data;
        this.response = response;
    }

    public void create() {
        setFileName(response, mapToFileName());

        Sheet sheet = workbook.createSheet();

        createHead(sheet, mapToHeadList());

        createBody(sheet, mapToBodyList());
    }

    private String mapToFileName() {
        return (String) data.get("filename");
    }

    @SuppressWarnings("unchecked")
    private List<String> mapToHeadList() {
        return (List<String>) data.get("head");
    }

    @SuppressWarnings("unchecked")
    private List<List<String>> mapToBodyList() {
        return (List<List<String>>) data.get("body");
    }

    private void setFileName(HttpServletResponse response, String fileName) {
        response.setHeader("Content-Disposition",
                "attachment; filename=\"" + getFileExtension(fileName) + "\"");
    }

    private String getFileExtension(String fileName) {
        if (workbook instanceof XSSFWorkbook) {
            fileName += ".xlsx";
        }
        if (workbook instanceof SXSSFWorkbook) {
            fileName += ".xlsx";
        }
        if (workbook instanceof HSSFWorkbook) {
            fileName += ".xls";
        }

        return fileName;
    }

    private void createHead(Sheet sheet, List<String> headList) {
        createRow(sheet, headList, 0);
    }

    private void createBody(Sheet sheet, List<List<String>> bodyList) {
        int rowSize = bodyList.size();
        for (int i = 0; i < rowSize; i++) {
            createRow(sheet, bodyList.get(i), i + 1);
        }
    }

    private void createRow(Sheet sheet, List<String> cellList, int rowNum) {
        int size = cellList.size();
        Row row = sheet.createRow(rowNum);

        for (int i = 0; i < size; i++) {
            row.createCell(i).setCellValue(cellList.get(i));
        }
    }

    public static Map<String, Object> createExcelData(List<? extends ExcelDto> data, Class<?> target) {
        Map<String, Object> excelData = new HashMap<>();
        excelData.put("filename", createFileName(target));
        excelData.put("head", createHeaderName(target));
        excelData.put("body", createBodyData(data));
        return excelData;
    }

    private static List<String> createHeaderName(Class<?> header) {
        List<String> headData = new ArrayList<>();
        for (Field field : header.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(ExcelColumnName.class)) {
                String headerName = field.getAnnotation(ExcelColumnName.class).headerName();
                if (headerName.equals("")) {
                    headData.add(field.getName());
                } else {
                    headData.add(headerName);
                }
            }
        }
        return headData;
    }

    private static String createFileName(Class<?> file) {
        if (file.isAnnotationPresent(ExcelFileName.class)) {
            String filename = file.getAnnotation(ExcelFileName.class).filename();
            return filename.equals("") ? file.getSimpleName() : filename;
        }
        throw new RuntimeException("excel filename not exist");
    }

    private static List<List<String>> createBodyData(List<? extends ExcelDto> dataList) {
        List<List<String>> bodyData = new ArrayList<>();
        dataList.forEach(v -> bodyData.add(v.mapToList()));
        return bodyData;
    }
}
