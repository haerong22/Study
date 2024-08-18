package org.example.elscourse.domain.controller.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.example.elscourse.domain.entity.CourseRating;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CourseRatingControllerSwagger {

    @Operation(
            summary = "코스에 새로운 평점 추가",
            description = "지정된 코스에 새로운 평점을 추가합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공적으로 평점이 추가됨", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CourseRating.class))),
                    @ApiResponse(responseCode = "400", description = "잘못된 입력"),
                    @ApiResponse(responseCode = "500", description = "서버 내부 오류")
            }
    )
    ResponseEntity<CourseRating> addRating(
            @Parameter(description = "평점을 추가할 코스의 ID", required = true) Long courseId,
            @Parameter(description = "추가할 평점 객체", required = true) CourseRating rating
    );

    @Operation(
            summary = "기존 평점 수정",
            description = "기존 평점을 수정합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공적으로 평점이 수정됨", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CourseRating.class))),
                    @ApiResponse(responseCode = "404", description = "평점을 찾을 수 없음"),
                    @ApiResponse(responseCode = "500", description = "서버 내부 오류")
            }
    )
    ResponseEntity<CourseRating> updateRating(
            @Parameter(description = "수정할 코스의 ID", required = true) Long courseId,
            @Parameter(description = "수정할 평점의 ID", required = true) Long ratingId,
            @Parameter(description = "수정된 평점 객체", required = true) CourseRating rating
    );

    @Operation(
            summary = "평점 삭제",
            description = "지정된 ID의 평점을 삭제합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공적으로 평점이 삭제됨"),
                    @ApiResponse(responseCode = "404", description = "평점을 찾을 수 없음"),
                    @ApiResponse(responseCode = "500", description = "서버 내부 오류")
            }
    )
    ResponseEntity<CourseRating> deleteRating(
            @Parameter(description = "삭제할 코스의 ID", required = true) Long courseId,
            @Parameter(description = "삭제할 평점의 ID", required = true) Long ratingId
    );

    @Operation(
            summary = "코스의 모든 평점 조회",
            description = "지정된 코스의 모든 평점을 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공적으로 평점 목록을 조회함", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CourseRating.class))),
                    @ApiResponse(responseCode = "500", description = "서버 내부 오류")
            }
    )
    ResponseEntity<List<CourseRating>> getRatings(
            @Parameter(description = "평점을 조회할 코스의 ID", required = true) Long courseId
    );
}
