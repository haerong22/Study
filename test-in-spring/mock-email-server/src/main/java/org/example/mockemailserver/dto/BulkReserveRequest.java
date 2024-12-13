package org.example.mockemailserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BulkReserveRequest {
    private int templateId;
    private LinkedHashMap<Long, BulkReserveTemplateContent> templateData;
    private LocalDateTime sendAt;
}