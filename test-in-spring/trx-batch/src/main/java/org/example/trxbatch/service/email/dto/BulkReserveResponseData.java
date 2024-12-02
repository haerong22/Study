package org.example.trxbatch.service.email.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BulkReserveResponseData {
    private Long id;
    private int count;
}