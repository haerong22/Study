package com.example.restcontroller.user.controller;

import com.example.restcontroller.notice.repository.NoticeRepository;
import com.example.restcontroller.user.entity.User;
import com.example.restcontroller.user.exception.UserNotFoundException;
import com.example.restcontroller.user.model.*;
import com.example.restcontroller.user.repository.UserLoginHistoryRepository;
import com.example.restcontroller.user.repository.UserRepository;
import com.example.restcontroller.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ApiAdminUserController {

    private final UserRepository userRepository;
    private final NoticeRepository noticeRepository;
    private final UserLoginHistoryRepository userLoginHistoryRepository;
    private final UserService userService;

    @GetMapping("/api/admin/user")
    public ResponseEntity<?> chapter2_18() {
        UserCount userCount = UserCount.builder()
                .totalCount(userRepository.count())
                .data(userRepository.findAll())
                .build();

        return ResponseEntity.ok(userCount);
    }

    @GetMapping("/api/admin/user/{id}")
    public ResponseEntity<?> chapter2_19(@PathVariable Long id) {

        Optional<User> userEntity = userRepository.findById(id);
        if (userEntity.isEmpty()) {
            return ResponseEntity.badRequest().body(ResponseMessage.fail("사용자 정보가 존재하지 않습니다."));
        }
        return ResponseEntity.ok().body(ResponseMessage.success(userEntity.get()));
    }

    @GetMapping("/api/admin/user/search")
    public ResponseEntity<?> chapter2_20(@RequestBody UserSearch userSearch) {
        List<User> userList = userRepository.findByEmailContainsAndUserNameContainsAndPhoneContains(
                userSearch.getEmail(), userSearch.getUserName(), userSearch.getPhone()
        );

        return ResponseEntity.ok().body(ResponseMessage.success(userList));
    }

    @PatchMapping("/api/admin/user/{id}/status")
    public ResponseEntity<?> chapter2_21(@PathVariable Long id) {
        User userEntity = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("유저가 없습니다."));

        String userStatus = userEntity.changeUserStatus();
        userRepository.save(userEntity);

        return ResponseEntity.ok().body(userStatus);
    }

    @DeleteMapping("/api/admin/user/{id}")
    public ResponseEntity<?> chapter2_22(@PathVariable Long id) {
        User userEntity = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("유저가 없습니다."));

        if (noticeRepository.countByUser(userEntity) > 0) {
            return new ResponseEntity<>(ResponseMessage.fail("사용자가 작성한 공지사항이 있습니다."), HttpStatus.BAD_REQUEST);
        }
        userRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/admin/user/login/history")
    public ResponseEntity<?> chapter2_23() {
        return ResponseEntity.ok().body(ResponseMessage.success(userLoginHistoryRepository.findAll()));
    }

    @PatchMapping("/api/admin/user/{id}/lock")
    public ResponseEntity<?> chapter2_24(@PathVariable Long id) {
        User userEntity = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("유저가 없습니다."));

        if (userEntity.isLockYn()) {
            return ResponseEntity.badRequest().body(ResponseMessage.fail("이미 접속제한이 된 사용자 입니다."));
        }
        userEntity.setLockYn(true);
        userRepository.save(userEntity);

        return ResponseEntity.ok().body(ResponseMessage.success());
    }

    @PatchMapping("/api/admin/user/{id}/unlock")
    public ResponseEntity<?> chapter2_25(@PathVariable Long id) {
        User userEntity = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("유저가 없습니다."));

        if (!userEntity.isLockYn()) {
            return ResponseEntity.badRequest().body(ResponseMessage.fail("접속 가능한 사용자 입니다."));
        }
        userEntity.setLockYn(false);
        userRepository.save(userEntity);

        return ResponseEntity.ok().body(ResponseMessage.success());
    }

    @GetMapping("/api/admin/user/status/count")
    public ResponseEntity<?> chapter2_26() {
        UserSummary userSummary = userService.getUserStatusCount();
        return ResponseEntity.ok().body(ResponseMessage.success(userSummary));
    }

    @GetMapping("/api/admin/user/today")
    public ResponseEntity<?> chapter2_27() {
        List<UserResponse> todayUsers = userService.getTodayUsers();
        return ResponseEntity.ok().body(ResponseMessage.success(todayUsers));
    }

    @GetMapping("/api/admin/user/notice/count")
    public ResponseEntity<?> chapter2_28() {
        List<UserNoticeCount> userNoticeCountList = userService.getUserNoticeCount();
        return ResponseEntity.ok().body(ResponseMessage.success(userNoticeCountList));
    }

    @GetMapping("/api/admin/user/log/count")
    public ResponseEntity<?> chapter2_29() {
        List<UserLogCount> userLogCountList = userService.getUserLogCount();
        return ResponseEntity.ok().body(ResponseMessage.success(userLogCountList));
    }

    @GetMapping("/api/admin/user/like/best")
    public ResponseEntity<?> chapter2_30() {
        List<UserLogCount> userLogCountList = userService.getUserLikeBest();
        return ResponseEntity.ok().body(ResponseMessage.success(userLogCountList));
    }
}
