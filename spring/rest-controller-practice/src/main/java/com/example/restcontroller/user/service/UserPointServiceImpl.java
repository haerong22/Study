package com.example.restcontroller.user.service;


import com.example.restcontroller.board.model.ServiceResult;
import com.example.restcontroller.common.exception.BizException;
import com.example.restcontroller.user.entity.User;
import com.example.restcontroller.user.entity.UserInterest;
import com.example.restcontroller.user.entity.UserPoint;
import com.example.restcontroller.user.entity.UserStatus;
import com.example.restcontroller.user.model.*;
import com.example.restcontroller.user.repository.UserCustomRepository;
import com.example.restcontroller.user.repository.UserInterestRepository;
import com.example.restcontroller.user.repository.UserPointRepository;
import com.example.restcontroller.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserPointServiceImpl implements UserPointService {

    private final UserPointRepository userPointRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public ServiceResult addPoint(String email, UserPointInput userPointInput) {
        User userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new BizException("회원 정보가 존재하지 않습니다."));

        userPointRepository.save(UserPoint.builder()
                .user(userEntity)
                .userPointType(userPointInput.getUserPointType())
                .point(userPointInput.getUserPointType().getValue())
                .build());
        return ServiceResult.success();
    }
}
