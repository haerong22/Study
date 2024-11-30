package org.example.trxbatch.exception;

import org.example.trxbatch.exception.enums.TrxBatchExceptionCode;

public class TrxBatchUnexpectedException extends TrxBatchException {

    private static final TrxBatchExceptionCode CODE = TrxBatchExceptionCode.UNEXPECTED;

    public TrxBatchUnexpectedException() {
        super(CODE);
    }

    public TrxBatchUnexpectedException(String message) {
        super(CODE, message);
    }

    public TrxBatchUnexpectedException(Throwable cause) {
        super(CODE, cause);
    }
}