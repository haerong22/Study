package org.example.mockemailserver.dto.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private ResponseCode responseCode;
    private String responseMessage;
    private T data;
}