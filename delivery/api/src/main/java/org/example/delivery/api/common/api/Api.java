package org.example.delivery.api.common.api;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.delivery.api.common.error.ErrorCode;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Api<T> {

    private Result result;

    @Valid
    private T body;

    public static <T> Api<T> ok(T data) {
        Api<T> api = new Api<>();
        api.result = Result.ok();
        api.body = data;
        return api;
    }

    public static <T> Api<T> error(ErrorCode errorCode) {
        return error(errorCode, "에러");
    }

    public static <T> Api<T> error(ErrorCode errorCode, Throwable tx) {
        return error(errorCode, tx);
    }

    public static <T> Api<T> error(ErrorCode errorCode, String description) {
        Api<T> api = new Api<>();
        api.result = Result.error(errorCode, description);
        return api;
    }
}
