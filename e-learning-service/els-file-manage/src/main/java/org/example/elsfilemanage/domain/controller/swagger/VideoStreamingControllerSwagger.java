package org.example.elsfilemanage.domain.controller.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface VideoStreamingControllerSwagger {

    @Operation(summary = "세션 비디오 스트리밍", description = "지정된 세션의 비디오 파일을 스트리밍합니다.", responses = {
            @ApiResponse(responseCode = "200", description = "성공적으로 비디오 스트리밍 시작", content = @Content(mediaType = "video/mp4")),
            @ApiResponse(responseCode = "206", description = "부분 비디오 스트리밍 시작", content = @Content(mediaType = "video/mp4")),
            @ApiResponse(responseCode = "404", description = "비디오 파일을 찾을 수 없음"),
            @ApiResponse(responseCode = "416", description = "잘못된 범위 요청"),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    }
    )
    ResponseEntity<?> streamVideo(
            @Parameter(description = "비디오를 스트리밍할 세션의 ID", required = true) Long sessionId,
            @Parameter(description = "HTTP 요청 객체", hidden = true) HttpServletRequest request
    );
}
