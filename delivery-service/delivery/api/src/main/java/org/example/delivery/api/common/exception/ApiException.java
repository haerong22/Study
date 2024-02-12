package org.example.delivery.api.common.exception;

import lombok.Getter;
import org.example.delivery.api.common.error.ErrorCode;

@Getter
public class ApiException extends RuntimeException implements CustomException {

    private final ErrorCode errorCode;
    private final String errorDescription;

    public ApiException(ErrorCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
        this.errorDescription = errorCode.getDescription();
    }

    public ApiException(ErrorCode errorCode, String errorDescription) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
    }

    public ApiException(ErrorCode errorCode, Throwable tx) {
        super(tx);
        this.errorCode = errorCode;
        this.errorDescription = errorCode.getDescription();
    }

    public ApiException(ErrorCode errorCode, Throwable tx, String errorDescription) {
        super(tx);
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
    }
}
