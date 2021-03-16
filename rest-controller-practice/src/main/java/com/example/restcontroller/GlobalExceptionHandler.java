package com.example.restcontroller;

import com.example.restcontroller.board.exception.BoardTypeNotFoundException;
import com.example.restcontroller.common.exception.BizException;
import com.example.restcontroller.common.model.ResponseResult;
import com.example.restcontroller.notice.exception.AlreadyDeletedException;
import com.example.restcontroller.notice.exception.DuplicateNoticeException;
import com.example.restcontroller.notice.exception.NoticeNotFoundException;
import com.example.restcontroller.user.exception.ExistsEmailException;
import com.example.restcontroller.user.exception.PasswordNotMatchException;
import com.example.restcontroller.user.exception.UserNotFoundException;
import com.example.restcontroller.user.model.ResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ NoticeNotFoundException.class,
                        DuplicateNoticeException.class,
                        UserNotFoundException.class,
                        ExistsEmailException.class,
                        PasswordNotMatchException.class,
                        BoardTypeNotFoundException.class,
                        BizException.class })
    public ResponseEntity<?> badRequest(RuntimeException e) {
        log.info(e.getClass().getName() + e.getMessage());
        return ResponseResult.fail(e.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> internalServerError(DataIntegrityViolationException e) {
        log.info(e.getMessage());
        return new ResponseEntity<>("회원 가입 실패", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AlreadyDeletedException.class)
    public ResponseEntity<?> ok(AlreadyDeletedException e) {
        log.info(e.getMessage());
        return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> exception(Exception e) {
        log.info(e.getMessage());
        return ResponseResult.fail(e.getMessage());
    }
}
