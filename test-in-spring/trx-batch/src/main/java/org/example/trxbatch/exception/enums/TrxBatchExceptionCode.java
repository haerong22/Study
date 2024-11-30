package org.example.trxbatch.exception.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TrxBatchExceptionCode {
    UNEXPECTED("UNEXPECTED"),
    NOT_SUPPORTED("NOT_SUPPORTED"),
    ;

    private final String dbCode;
}