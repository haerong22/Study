package com.example.restcontroller.user.service;


import com.example.restcontroller.user.entity.UserStatus;
import com.example.restcontroller.user.model.UserNoticeCount;
import com.example.restcontroller.user.model.UserLogCount;
import com.example.restcontroller.user.model.UserResponse;
import com.example.restcontroller.user.model.UserSummary;
import com.example.restcontroller.user.repository.UserCustomRepository;
import com.example.restcontroller.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserCustomRepository userCustomRepository;

    @Override
    public UserSummary getUserStatusCount() {
        Long usingUserCount = userRepository.countByStatus(UserStatus.USING);
        Long stopUserCount = userRepository.countByStatus(UserStatus.STOP);
        Long totalUserCount = userRepository.count();
        return UserSummary.builder()
                .stopUserCount(stopUserCount)
                .usingUserCount(usingUserCount)
                .totalUserCount(totalUserCount)
                .build();
    }

    @Override
    public List<UserResponse> getTodayUsers() {

        LocalDateTime startDate = LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0));
        LocalDateTime endDate = startDate.plusDays(1);

        return userRepository.findTodayUsers(startDate, endDate)
                .stream().map(UserResponse::of).collect(Collectors.toList());
    }

    @Override
    public List<UserNoticeCount> getUserNoticeCount() {
        return userCustomRepository.findUserNoticeCount();
    }

    @Override
    public List<UserLogCount> getUserLogCount() {
        return userCustomRepository.findUserLogCount();
    }

    @Override
    public List<UserLogCount> getUserLikeBest() {
        return userCustomRepository.findUserLikeBest();
    }
}
