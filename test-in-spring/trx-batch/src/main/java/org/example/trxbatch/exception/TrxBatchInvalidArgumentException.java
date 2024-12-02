package org.example.trxbatch.exception;

import org.example.trxbatch.exception.enums.TrxBatchExceptionCode;

public class TrxBatchInvalidArgumentException extends TrxBatchException {

    private static final TrxBatchExceptionCode CODE = TrxBatchExceptionCode.INVALID_ARGUMENT;

    public TrxBatchInvalidArgumentException() {
        super(CODE);
    }

    public TrxBatchInvalidArgumentException(String message) {
        super(CODE, message);
    }

    public TrxBatchInvalidArgumentException(Throwable cause) {
        super(CODE, cause);
    }
}