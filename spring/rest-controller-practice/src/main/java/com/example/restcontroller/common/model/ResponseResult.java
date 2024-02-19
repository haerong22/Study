package com.example.restcontroller.common.model;

import com.example.restcontroller.board.model.ServiceResult;
import com.example.restcontroller.user.model.ResponseMessage;
import org.springframework.http.ResponseEntity;

public class ResponseResult {


    public static ResponseEntity<?> fail(String message) {
        return fail(message, null);
    }

    public static <T> ResponseEntity<?> fail(String message, T data) {
        return ResponseEntity.badRequest().body(ResponseMessage.fail(message, data));
    }

    public static ResponseEntity<?> success() {
        return ResponseEntity.ok().body(ResponseMessage.success());
    }

    public static <T> ResponseEntity<?> success(T data) {
        return ResponseEntity.ok().body(ResponseMessage.success(data));
    }

    public static ResponseEntity<?> result(ServiceResult result) {
        if (result.isFail()) {
            return fail(result.getMessage());
        }
        return success();
    }
}
