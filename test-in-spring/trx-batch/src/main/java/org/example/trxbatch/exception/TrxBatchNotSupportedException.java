package org.example.trxbatch.exception;

import org.example.trxbatch.exception.enums.TrxBatchExceptionCode;

public class TrxBatchNotSupportedException extends TrxBatchException {

    private static final TrxBatchExceptionCode CODE = TrxBatchExceptionCode.NOT_SUPPORTED;

    public TrxBatchNotSupportedException() {
        super(CODE);
    }

    public TrxBatchNotSupportedException(String message) {
        super(CODE, message);
    }

    public TrxBatchNotSupportedException(Throwable cause) {
        super(CODE, cause);
    }
}