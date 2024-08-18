package org.example.elscourse.domain.controller.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.example.elscourse.domain.entity.CourseSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface CourseSessionControllerSwagger {

    @Operation(
            summary = "코스에 새로운 세션 추가",
            description = "지정된 코스에 새로운 세션을 추가합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공적으로 세션이 추가됨", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CourseSession.class))),
                    @ApiResponse(responseCode = "400", description = "잘못된 입력"),
                    @ApiResponse(responseCode = "500", description = "서버 내부 오류")
            }
    )
    ResponseEntity<CourseSession> addSession(
            @Parameter(description = "세션을 추가할 코스의 ID", required = true) Long courseId,
            @Parameter(description = "추가할 세션 객체", required = true) CourseSession courseSession
    );

    @Operation(
            summary = "기존 세션 수정",
            description = "기존 세션을 수정합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공적으로 세션이 수정됨", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CourseSession.class))),
                    @ApiResponse(responseCode = "404", description = "세션을 찾을 수 없음"),
                    @ApiResponse(responseCode = "500", description = "서버 내부 오류")
            }
    )
    ResponseEntity<CourseSession> updateSession(
            @Parameter(description = "수정할 코스의 ID", required = true) Long courseId,
            @Parameter(description = "수정할 세션의 ID", required = true) Long sessionId,
            @Parameter(description = "수정된 세션 객체", required = true) CourseSession courseSession
    );

    @Operation(
            summary = "ID로 세션 조회",
            description = "ID로 세션을 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공적으로 세션을 조회함", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CourseSession.class))),
                    @ApiResponse(responseCode = "404", description = "세션을 찾을 수 없음"),
                    @ApiResponse(responseCode = "500", description = "서버 내부 오류")
            }
    )
    ResponseEntity<CourseSession> getSession(
            @Parameter(description = "조회할 세션의 ID", required = true) Long courseId,
            @PathVariable Long sessionId
    );

    @Operation(
            summary = "코스의 모든 세션 조회",
            description = "지정된 코스의 모든 세션을 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공적으로 세션 목록을 조회함", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CourseSession.class))),
                    @ApiResponse(responseCode = "500", description = "서버 내부 오류")
            }
    )
    ResponseEntity<List<CourseSession>> getSessions(
            @Parameter(description = "세션을 조회할 코스의 ID", required = true) Long courseId
    );
}
