package com.example.restcontroller.user.service;


import com.example.restcontroller.board.model.ServiceResult;
import com.example.restcontroller.common.exception.BizException;
import com.example.restcontroller.logs.service.LogService;
import com.example.restcontroller.user.entity.User;
import com.example.restcontroller.user.entity.UserInterest;
import com.example.restcontroller.user.entity.UserStatus;
import com.example.restcontroller.user.model.*;
import com.example.restcontroller.user.repository.UserCustomRepository;
import com.example.restcontroller.user.repository.UserInterestRepository;
import com.example.restcontroller.user.repository.UserRepository;
import com.example.restcontroller.util.PasswordUtils;
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
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserCustomRepository userCustomRepository;
    private final UserInterestRepository userInterestRepository;

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

    @Transactional
    @Override
    public ServiceResult addInterestUser(Long id, String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (!optionalUser.isPresent()) {
            return ServiceResult.fail("회원 정보가 없습니다.");
        }
        User userEntity = optionalUser.get();

        Optional<User> optionalInterestUser = userRepository.findById(id);
        if (!optionalInterestUser.isPresent()) {
            return ServiceResult.fail("관심 사용자에 추가할 회원 정보가 없습니다.");
        }
        User interestUserEntity = optionalInterestUser.get();

        if (userEntity.getId() == interestUserEntity.getId()) {
            return ServiceResult.fail("자기 자신은 추가할 수 없습니다.");
        }

        if (userInterestRepository.countByUserAndInterestUser(userEntity, interestUserEntity) > 0) {
            return ServiceResult.fail("이미 관심사용자 목록에 추가된 사용자 입니다.");
        }

        UserInterest userInterest = UserInterest.builder()
                .user(userEntity)
                .interestUser(interestUserEntity)
                .regDate(LocalDateTime.now())
                .build();
        userInterestRepository.save(userInterest);

        return ServiceResult.success();
    }

    @Transactional
    @Override
    public ServiceResult deleteInterestUser(Long id, String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (!optionalUser.isPresent()) {
            return ServiceResult.fail("회원 정보가 없습니다.");
        }
        User userEntity = optionalUser.get();

        Optional<User> optionalInterestUser = userRepository.findById(id);
        if (!optionalInterestUser.isPresent()) {
            return ServiceResult.fail("관심 사용자에 추가할 회원 정보가 없습니다.");
        }
        User interestUserEntity = optionalInterestUser.get();

        Optional<UserInterest> optionalUserInterest = userInterestRepository.findByUserAndInterestUser(userEntity, interestUserEntity);

        if (!optionalUserInterest.isPresent()) {
            return ServiceResult.fail("삭제할 정보가 없습니다.");
        }
        UserInterest userInterestEntity = optionalUserInterest.get();

        if (userInterestEntity.getUser().getId() != userEntity.getId()) {
            return ServiceResult.fail("자신의 관심자 정보만 삭제 할 수 있습니다.");
        }

        userInterestRepository.delete(userInterestEntity);
        return ServiceResult.success();
    }

    @Override
    public User login(UserLogin userLogin) {

        User userEntity = userRepository.findByEmail(userLogin.getEmail())
                .orElseThrow(() -> new BizException("회원 정보가 존재하지 않습니다."));

        if (!PasswordUtils.equalPassword(userLogin.getPassword(), userEntity.getPassword())) {
            throw new BizException("일치하는 정보가 없습니다.");
        }

        return userEntity;
    }
}
