package org.example.elscourse.domain.controller.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.example.elscourse.domain.entity.Course;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CourseControllerSwagger {

    @Operation(
            summary = "새로운 코스 생성",
            description = "새로운 코스를 생성합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공적으로 코스가 생성됨", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Course.class))),
                    @ApiResponse(responseCode = "400", description = "잘못된 입력"),
                    @ApiResponse(responseCode = "500", description = "서버 내부 오류")
            }
    )
    ResponseEntity<Course> saveCourse(
            @Parameter(description = "생성할 코스 객체", required = true) Course course
    );

    @Operation(
            summary = "기존 코스 수정",
            description = "기존 코스를 수정합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공적으로 코스가 수정됨", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Course.class))),
                    @ApiResponse(responseCode = "404", description = "코스를 찾을 수 없음"),
                    @ApiResponse(responseCode = "500", description = "서버 내부 오류")
            }
    )
    ResponseEntity<Course> updateCourse(
            @Parameter(description = "수정할 코스의 ID", required = true) Long courseId,
            @Parameter(description = "수정된 코스 객체", required = true) Course course
    );

    @Operation(
            summary = "ID로 코스 조회",
            description = "ID로 코스를 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공적으로 코스를 조회함", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Course.class))),
                    @ApiResponse(responseCode = "404", description = "코스를 찾을 수 없음"),
                    @ApiResponse(responseCode = "500", description = "서버 내부 오류")
            }
    )
    ResponseEntity<Course> getCourse(
            @Parameter(description = "조회할 코스의 ID", required = true) Long courseId
    );

    @Operation(
            summary = "모든 코스 조회 또는 ID로 필터링",
            description = "모든 코스를 조회하거나 ID로 필터링합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공적으로 코스 목록을 조회함", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Course.class))),
                    @ApiResponse(responseCode = "500", description = "서버 내부 오류")
            }
    )
    ResponseEntity<List<Course>> getAllCourses(
            @Parameter(description = "선택 사항: 필터링할 코스 ID 목록") List<Long> courseIds
    );
}
