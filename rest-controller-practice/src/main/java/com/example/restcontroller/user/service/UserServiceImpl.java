package com.example.restcontroller.user.service;


import com.example.restcontroller.board.model.ServiceResult;
import com.example.restcontroller.user.entity.User;
import com.example.restcontroller.user.entity.UserInterest;
import com.example.restcontroller.user.entity.UserStatus;
import com.example.restcontroller.user.model.UserNoticeCount;
import com.example.restcontroller.user.model.UserLogCount;
import com.example.restcontroller.user.model.UserResponse;
import com.example.restcontroller.user.model.UserSummary;
import com.example.restcontroller.user.repository.UserCustomRepository;
import com.example.restcontroller.user.repository.UserInterestRepository;
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
}
