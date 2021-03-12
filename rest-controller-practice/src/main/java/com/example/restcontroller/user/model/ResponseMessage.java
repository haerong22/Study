package com.example.restcontroller.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseMessage<T> {

    private ResponseMessageHeader header;

    private T body;

    public static <T> ResponseMessage<?> fail(String message, T data) {
        return ResponseMessage.builder()
                .header(ResponseMessageHeader.builder()
                        .result(false)
                        .resultCode(HttpStatus.BAD_REQUEST.value())
                        .message(message)
                        .status(HttpStatus.BAD_REQUEST)
                        .build())
                .body(data)
                .build();
    }

    public static ResponseMessage<?> fail(String message) {
        return fail(message, null);
    }

    public static <T> ResponseMessage<?> success(T data) {
        return ResponseMessage.builder()
                .header(ResponseMessageHeader.builder()
                        .result(true)
                        .resultCode(HttpStatus.OK.value())
                        .message("")
                        .status(HttpStatus.OK)
                        .build())
                .body(data)
                .build();
    }

    public static ResponseMessage<?> success() {
        return success(null);
    }
}
