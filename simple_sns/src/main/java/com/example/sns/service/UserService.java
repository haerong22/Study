package com.example.sns.service;

import com.example.sns.exception.SnsApplicationException;
import com.example.sns.model.Alarm;
import com.example.sns.model.User;
import com.example.sns.model.entity.UserEntity;
import com.example.sns.repository.AlarmEntityRepository;
import com.example.sns.repository.UserCacheRepository;
import com.example.sns.repository.UserEntityRepository;
import com.example.sns.util.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.sns.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserEntityRepository userEntityRepository;
    private final AlarmEntityRepository alarmEntityRepository;
    private final BCryptPasswordEncoder encoder;
    private final UserCacheRepository userCacheRepository;

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.token.expired-time-ms}")
    private Long expiredTimeMs;

    public User loadUserByUsername(String username) {
        return userCacheRepository.getUser(username).orElseGet(() ->
            userEntityRepository.findByUsername(username).map(User::fromEntity)
                    .orElseThrow(
                            () -> new SnsApplicationException(USER_NOT_FOUND, String.format("%s not founded", username))
                    )
        );
    }

    @Transactional
    public User join(String username, String password) {

        // username 확인
        userEntityRepository.findByUsername(username).ifPresent(it -> {
            throw new SnsApplicationException(DUPLICATED_USER_NAME, String.format("%s is duplicated", username));
        });

        // 회원가입 진행
        UserEntity userEntity = userEntityRepository.save(UserEntity.of(username, encoder.encode(password)));

        return User.fromEntity(userEntity);
    }

    public String login(String username, String password) {

        // 회원가입 여부 체크
        User user = loadUserByUsername(username);

        userCacheRepository.setUser(user);

        // 비밀번호 체크
        if (!encoder.matches(password, user.getPassword())) {
            throw new SnsApplicationException(INVALID_PASSWORD);
        }

        // 토큰 생성
        return JwtTokenUtils.generateToken(username, secretKey, expiredTimeMs);
    }

    public Page<Alarm> alarmList(Integer userId, Pageable pageable) {
//        UserEntity userEntity = userEntityRepository.findByUsername(username)
//                .orElseThrow(
//                        () -> new SnsApplicationException(USER_NOT_FOUND, String.format("%s not founded", username))
//                );

        return alarmEntityRepository.findAllByUserId(userId, pageable).map(Alarm::fromEntity);
    }
}
