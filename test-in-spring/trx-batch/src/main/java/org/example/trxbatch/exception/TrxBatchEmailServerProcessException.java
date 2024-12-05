package org.example.trxbatch.exception;

import org.example.trxbatch.exception.enums.TrxBatchExceptionCode;

public class TrxBatchEmailServerProcessException extends TrxBatchException {

    private static final TrxBatchExceptionCode CODE = TrxBatchExceptionCode.EMAIL_SERVER_PROCESS_ERROR;

    public TrxBatchEmailServerProcessException() {
        super(CODE);
    }

    public TrxBatchEmailServerProcessException(String message) {
        super(CODE, message);
    }
}