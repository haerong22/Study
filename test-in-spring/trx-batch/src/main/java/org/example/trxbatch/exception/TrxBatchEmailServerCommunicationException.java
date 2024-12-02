package org.example.trxbatch.exception;

import org.example.trxbatch.exception.enums.TrxBatchExceptionCode;

public class TrxBatchEmailServerCommunicationException extends TrxBatchException {

    private static final TrxBatchExceptionCode CODE = TrxBatchExceptionCode.EMAIL_SERVER_COMMUNICATION_ERROR;

    public TrxBatchEmailServerCommunicationException() {
        super(CODE);
    }

    public TrxBatchEmailServerCommunicationException(String message) {
        super(CODE, message);
    }
}