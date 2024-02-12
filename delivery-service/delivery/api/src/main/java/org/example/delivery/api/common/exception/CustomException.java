package org.example.delivery.api.common.exception;

import org.example.delivery.api.common.error.ErrorCode;

public interface CustomException {

    ErrorCode getErrorCode();
    String getErrorDescription();
}
