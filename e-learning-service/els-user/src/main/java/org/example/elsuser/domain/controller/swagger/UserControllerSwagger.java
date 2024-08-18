package org.example.elsuser.domain.controller.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.example.elsuser.domain.dto.PasswordChangeDTO;
import org.example.elsuser.domain.dto.UserDTO;
import org.example.elsuser.domain.entity.User;
import org.example.elsuser.domain.entity.UserLoginHistory;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserControllerSwagger {

    @Operation(
            summary = "사용자 생성",
            description = "새로운 사용자를 생성합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공적으로 사용자 생성", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
                    @ApiResponse(responseCode = "409", description = "중복된 사용자 존재", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "500", description = "서버 내부 오류", content = @Content(mediaType = "application/json"))
            }
    )
    ResponseEntity<User> createUser(
            @Parameter(description = "생성할 사용자 정보", required = true) UserDTO userDTO
    );

    @Operation(
            summary = "사용자 조회",
            description = "ID로 사용자를 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공적으로 사용자 조회", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
                    @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "500", description = "서버 내부 오류", content = @Content(mediaType = "application/json"))
            }
    )
    ResponseEntity<User> getUserById(
            @Parameter(description = "조회할 사용자의 ID", required = true) Integer userId
    );

    @Operation(
            summary = "사용자 정보 수정",
            description = "ID로 사용자의 정보를 수정합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공적으로 사용자 정보 수정", content = @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))),
                    @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "500", description = "서버 내부 오류", content = @Content(mediaType = "application/json"))
            }
    )
    ResponseEntity<User> updateUser(
            @Parameter(description = "수정할 사용자의 ID", required = true) Integer userId,
            @Parameter(description = "수정할 사용자 정보", required = true) UserDTO userDTO
    );

    @Operation(
            summary = "사용자 로그인 기록 조회",
            description = "ID로 사용자의 로그인 기록을 조회합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공적으로 로그인 기록 조회", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserLoginHistory.class))),
                    @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "500", description = "서버 내부 오류", content = @Content(mediaType = "application/json"))
            }
    )
    ResponseEntity<List<UserLoginHistory>> getUserLoginHistories(
            @Parameter(description = "로그인 기록을 조회할 사용자의 ID", required = true) Integer userId
    );

    @Operation(
            summary = "사용자 비밀번호 변경",
            description = "ID로 사용자의 비밀번호를 변경합니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공적으로 비밀번호 변경", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "403", description = "권한 없음", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "500", description = "서버 내부 오류", content = @Content(mediaType = "application/json"))
            }
    )
    ResponseEntity<Void> changePassword(
            @Parameter(description = "비밀번호를 변경할 사용자의 ID", required = true) Integer userId,
            @Parameter(description = "비밀번호 변경 요청 정보", required = true) PasswordChangeDTO passwordChangeDTO
    );
}
