package org.example.delivery.api.common.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.delivery.api.common.error.CommonErrorCode;
import org.example.delivery.api.common.error.ErrorCode;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Result {

    private Integer resultCode;
    private String resultMessage;
    private String resultDescription;

    protected static Result ok() {
        return Result.builder()
                .resultCode(CommonErrorCode.OK.getErrorCode())
                .resultMessage(CommonErrorCode.OK.getDescription())
                .resultDescription("성공")
                .build();
    }

    protected static Result error(ErrorCode errorCode) {
        return error(errorCode, "에러");
    }

    protected static Result error(ErrorCode errorCode, Throwable tx) {
        return error(errorCode, tx.getLocalizedMessage());
    }

    protected static Result error(ErrorCode errorCode, String description) {
        return Result.builder()
                .resultCode(errorCode.getErrorCode())
                .resultMessage(errorCode.getDescription())
                .resultDescription(description)
                .build();
    }
}
