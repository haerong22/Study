package com.example.restcontroller.user.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.example.restcontroller.board.entity.Board;
import com.example.restcontroller.board.entity.BoardComment;
import com.example.restcontroller.board.model.ServiceResult;
import com.example.restcontroller.board.service.BoardService;
import com.example.restcontroller.common.model.ResponseResult;
import com.example.restcontroller.notice.entity.Notice;
import com.example.restcontroller.notice.entity.NoticeLike;
import com.example.restcontroller.notice.exception.NoticeNotFoundException;
import com.example.restcontroller.notice.model.NoticeResponse;
import com.example.restcontroller.notice.model.ResponseError;
import com.example.restcontroller.notice.repository.NoticeLikeRepository;
import com.example.restcontroller.notice.repository.NoticeRepository;
import com.example.restcontroller.user.entity.User;
import com.example.restcontroller.user.exception.ExistsEmailException;
import com.example.restcontroller.user.exception.PasswordNotMatchException;
import com.example.restcontroller.user.exception.UserNotFoundException;
import com.example.restcontroller.user.model.*;
import com.example.restcontroller.user.repository.UserRepository;
import com.example.restcontroller.user.service.UserPointService;
import com.example.restcontroller.user.service.UserService;
import com.example.restcontroller.util.JWTUtils;
import com.example.restcontroller.util.PasswordUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ApiUserController {

    private final UserRepository userRepository;
    private final NoticeRepository noticeRepository;
    private final NoticeLikeRepository noticeLikeRepository;

    private final BoardService boardService;
    private final UserPointService userPointService;
    private final UserService userService;

    @PostMapping("/api/user")
    public ResponseEntity<?> chapter2_1(@RequestBody @Valid UserInput userInput, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            List<ResponseError> errors = bindingResult.getFieldErrors().stream()
                    .map(ResponseError::of)
                    .collect(Collectors.toList());

            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/api/user2")
    public ResponseEntity<?> chapter2_2(@RequestBody @Valid UserInput userInput, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            List<ResponseError> errors = bindingResult.getFieldErrors().stream()
                    .map(ResponseError::of)
                    .collect(Collectors.toList());

            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        userRepository.save(new User(userInput));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/api/user/{id}")
    public ResponseEntity<?> chapter2_3(@PathVariable Long id, @RequestBody @Valid UserUpdate userUpdate, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            List<ResponseError> errors = bindingResult.getFieldErrors().stream()
                    .map(ResponseError::of)
                    .collect(Collectors.toList());

            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        User userEntity = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("사용자 정보가 없습니다."));

        User user = User.updateUser(userEntity, userUpdate.getPhone());
        userRepository.save(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/api/user/{id}")
    public ResponseEntity<?> chapter2_4(@PathVariable Long id) {
        User userEntity = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("사용자 정보가 없습니다."));

//        UserResponse response = new UserResponse(userEntity);
        UserResponse response = UserResponse.of(userEntity);

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping("/api/user/{id}/notice")
    public List<NoticeResponse> chapter2_5(@PathVariable Long id) {
        User userEntity = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("사용자 정보가 없습니다."));

        List<Notice> noticeList = noticeRepository.findByUser(userEntity)
                .orElseThrow(() -> new NoticeNotFoundException("공지사항이 없습니다."));

        return noticeList.stream().map(NoticeResponse::of).collect(Collectors.toList());
    }

    @PostMapping("/api/user3")
    public ResponseEntity<?> chapter2_6(@RequestBody @Valid UserInput userInput, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            List<ResponseError> errors = bindingResult.getFieldErrors().stream()
                    .map(ResponseError::of)
                    .collect(Collectors.toList());

            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        if (userRepository.countByEmail(userInput.getEmail()) > 0) {
            throw new ExistsEmailException("이미 존재하는 이메일 입니다.");
        }

        userRepository.save(new User(userInput));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping("/api/user/{id}/password")
    public ResponseEntity<?> chapter2_7(@PathVariable Long id, @RequestBody UserPasswordUpdate userPasswordUpdate, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            List<ResponseError> errors = bindingResult.getFieldErrors().stream()
                    .map(ResponseError::of)
                    .collect(Collectors.toList());

            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        User userEntity = userRepository.findByIdAndPassword(id, userPasswordUpdate.getPassword())
                .orElseThrow(() -> new PasswordNotMatchException("비밀번호가 일치하지 않습니다."));

        userEntity.setPassword(userPasswordUpdate.getNewPassword());

        userRepository.save(userEntity);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private String getEncryptPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    @PostMapping("/api/user4")
    public ResponseEntity<?> chapter2_8(@RequestBody @Valid UserInput userInput, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            List<ResponseError> errors = bindingResult.getFieldErrors().stream()
                    .map(ResponseError::of)
                    .collect(Collectors.toList());

            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        userRepository.save(User.builder()
                .email(userInput.getEmail())
                .userName(userInput.getUserName())
                .password(getEncryptPassword(userInput.getPassword()))
                .phone(userInput.getPhone())
                .regDate(LocalDateTime.now())
                .build());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/api/user/{id}")
    public ResponseEntity<?> chapter2_9(@PathVariable Long id) {
        User userEntity = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("사용자가 없습니다."));

        userRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/api/user")
    public ResponseEntity<?> chapter2_10(@Valid FindUserId findUserId, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            List<ResponseError> errors = bindingResult.getFieldErrors().stream()
                    .map(ResponseError::of)
                    .collect(Collectors.toList());

            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        User userEntity = userRepository.findByUserNameAndPhone(findUserId.getUserName(), findUserId.getPhone())
                .orElseThrow(() -> new UserNotFoundException("사용자가 없습니다."));

        return new ResponseEntity<>(userEntity.getEmail(), HttpStatus.OK);
    }

    private String getResetPassword() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10);
    }

    void sendSMS(String message) {
        System.out.println("문자메시지 전송 : " + message);
    }

    @GetMapping("/api/user/{id}/password/reset")
    public ResponseEntity<?> chapter2_11(@PathVariable Long id) {
        User userEntity = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("사용자가 없습니다."));

        String temp = getResetPassword();
        userEntity.setPassword(getEncryptPassword(temp));

        userRepository.save(userEntity);

        String message = String.format("[%s]님의 임시비밀번호가 [%s]로 초기화 되었습니다.",
                userEntity.getUserName(),
                temp);
        sendSMS(message);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/user/{id}/notice/like")
    public ResponseEntity<?> chapter2_12(@PathVariable Long id) {
        User userEntity = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("사용자가 없습니다."));

        List<NoticeLike> noticeLikeList = noticeLikeRepository.findByUser(userEntity);

        return ResponseEntity.ok().body(noticeLikeList);
    }

    @PostMapping("/api/user/login")
    public ResponseEntity<?> chapter2_13(@RequestBody @Valid UserLogin userLogin, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            List<ResponseError> errors = bindingResult.getFieldErrors().stream()
                    .map(ResponseError::of)
                    .collect(Collectors.toList());

            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        User userEntity = userRepository.findByEmail(userLogin.getEmail())
                .orElseThrow(() -> new UserNotFoundException("사용자가 없습니다."));

        if (!PasswordUtils.equalPassword(userLogin.getPassword(), userEntity.getPassword())) {
            throw new PasswordNotMatchException("비밀번호가 일치하지 않습니다.");
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/user/login2")
    public ResponseEntity<?> chapter2_14(@RequestBody @Valid UserLogin userLogin, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            List<ResponseError> errors = bindingResult.getFieldErrors().stream()
                    .map(ResponseError::of)
                    .collect(Collectors.toList());

            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        User userEntity = userRepository.findByEmail(userLogin.getEmail())
                .orElseThrow(() -> new UserNotFoundException("사용자가 없습니다."));

        if (!PasswordUtils.equalPassword(userLogin.getPassword(), userEntity.getPassword())) {
            throw new PasswordNotMatchException("비밀번호가 일치하지 않습니다.");
        }

        String token = JWT.create()
                .withExpiresAt(new Date())
                .withClaim("user_id", userEntity.getId())
                .withSubject(userEntity.getEmail())
                .withIssuer(userEntity.getEmail())
                .sign(Algorithm.HMAC512("kim".getBytes()));


        return ResponseEntity.ok().body(UserLoginToken.builder().token(token).build());
    }

    @PostMapping("/api/user/login3")
    public ResponseEntity<?> chapter2_15(@RequestBody @Valid UserLogin userLogin, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            List<ResponseError> errors = bindingResult.getFieldErrors().stream()
                    .map(ResponseError::of)
                    .collect(Collectors.toList());

            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        User userEntity = userRepository.findByEmail(userLogin.getEmail())
                .orElseThrow(() -> new UserNotFoundException("사용자가 없습니다."));

        if (!PasswordUtils.equalPassword(userLogin.getPassword(), userEntity.getPassword())) {
            throw new PasswordNotMatchException("비밀번호가 일치하지 않습니다.");
        }


        String token = JWT.create()
                .withExpiresAt(Timestamp.valueOf(LocalDateTime.now().plusMinutes(30)))
                .withClaim("user_id", userEntity.getId())
                .withSubject(userEntity.getEmail())
                .withIssuer(userEntity.getEmail())
                .sign(Algorithm.HMAC512("kim".getBytes()));


        return ResponseEntity.ok().body(UserLoginToken.builder().token(token).build());
    }

    @PatchMapping("/api/user/login")
    public ResponseEntity<?> chapter2_16(HttpServletRequest request) {
        String token = request.getHeader("TOKEN");

        String email;
        try {
            email = JWT.require(Algorithm.HMAC512("kim".getBytes()))
                    .build()
                    .verify(token)
                    .getIssuer();
        } catch (SignatureVerificationException e) {
            throw new PasswordNotMatchException("유효한 토큰이 아닙니다.");
        }

        User userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("사용자 정보 없음"));

        String newToken = JWT.create()
                .withExpiresAt(Timestamp.valueOf(LocalDateTime.now().plusMinutes(30)))
                .withClaim("user_id", userEntity.getId())
                .withSubject(userEntity.getEmail())
                .withIssuer(userEntity.getEmail())
                .sign(Algorithm.HMAC512("kim".getBytes()));

        return ResponseEntity.ok().body(UserLoginToken.builder().token(newToken).build());
    }

    @DeleteMapping("/api/user/login")
    public ResponseEntity<?> chapter2_17(@RequestHeader("TOKEN") String token) {
        try {
            String email = JWTUtils.getIssuer(token);
        } catch (SignatureVerificationException e) {
            return new ResponseEntity<>("토큰이 유효하지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        // 세션, 쿠키삭제
        // 클라이언트 쿠키/로컬스토리지/세션스토리지
        // 블랙리스트 작성
        // ...

        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/user/board/post")
    public ResponseEntity<?> chapter3_20(@RequestHeader("TOKEN") String token) {
        String email = "";
        try {
            email = JWTUtils.getIssuer(token);
        } catch (JWTVerificationException e) {
            return ResponseResult.fail("토큰 정보가 정확하지 않습니다.");
        }

        List<Board> list = boardService.postList(email);
        return ResponseResult.success(list);
    }

    @GetMapping("/api/user/board/comment")
    public ResponseEntity<?> chapter3_21(@RequestHeader("TOKEN") String token) {
        String email = "";
        try {
            email = JWTUtils.getIssuer(token);
        } catch (JWTVerificationException e) {
            return ResponseResult.fail("토큰 정보가 정확하지 않습니다.");
        }

        List<BoardComment> list = boardService.commentList(email);

        return ResponseResult.success(list);
    }

    @PostMapping("/api/user/point")
    public ResponseEntity<?> chapter3_22(@RequestHeader("TOKEN") String token,
                                         @RequestBody UserPointInput userPointInput) {
        String email = "";
        try {
            email = JWTUtils.getIssuer(token);
        } catch (JWTVerificationException e) {
            return ResponseResult.fail("토큰 정보가 정확하지 않습니다.");
        }
        ServiceResult result = userPointService.addPoint(email, userPointInput);

        return ResponseResult.result(result);
    }

    @PostMapping("/api/public/user")
    public ResponseEntity<?> chapter5_2(@RequestBody UserInput userInput) {

        ServiceResult result = userService.addUser(userInput);
        return ResponseResult.result(result);
    }
}