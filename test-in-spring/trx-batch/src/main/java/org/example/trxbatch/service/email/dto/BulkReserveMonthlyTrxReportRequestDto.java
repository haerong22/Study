package org.example.trxbatch.service.email.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;

@Data
public class BulkReserveMonthlyTrxReportRequestDto {
    private int templateId = 512;
    private LinkedHashMap<Long, BulkReserveMonthlyTrxReportRequestTemplateContent> templateData;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime sendAt;
}