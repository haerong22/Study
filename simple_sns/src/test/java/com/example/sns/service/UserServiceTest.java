package com.example.sns.service;

import com.example.sns.exception.SnsApplicationException;
import com.example.sns.fixture.UserEntityFixture;
import com.example.sns.model.entity.UserEntity;
import com.example.sns.repository.UserEntityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserEntityRepository userEntityRepository;

    @Test
    void 회원가입이_정상적으로_동작하는_경우() {
        String username = "username";
        String password = "password";

        when(userEntityRepository.findByUsername(username)).thenReturn(Optional.empty());
        when(userEntityRepository.save(any())).thenReturn(Optional.of(UserEntityFixture.get(username, password)));

        assertDoesNotThrow(() -> userService.join(username, password));
    }

    @Test
    void 회원가입시_username으로_회원가입한_유저가_이미_있는경우() {
        String username = "username";
        String password = "password";

        UserEntity fixture = UserEntityFixture.get(username, password);

        when(userEntityRepository.findByUsername(username)).thenReturn(Optional.of(fixture));
        when(userEntityRepository.save(any())).thenReturn(Optional.of(fixture));

        assertThrows(
                SnsApplicationException.class,
                () -> userService.join(username, password)
        );
    }

    @Test
    void 로그인이_정상적으로_동작하는_경우() {
        String username = "username";
        String password = "password";

        UserEntity fixture = UserEntityFixture.get(username, password);

        when(userEntityRepository.findByUsername(username)).thenReturn(Optional.of(fixture));

        assertDoesNotThrow(() -> userService.login(username, password));
    }

    @Test
    void 로그인시_username으로_회원가입한_유저가_없는_경우() {
        String username = "username";
        String password = "password";

        when(userEntityRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(
                SnsApplicationException.class,
                () -> userService.login(username, password)
        );
    }

    @Test
    void 로그인시_패스워드가_틀린_경우() {
        String username = "username";
        String password = "password";
        String wrongPassword = "wrongPassword";

        UserEntity fixture = UserEntityFixture.get(username, password);

        when(userEntityRepository.findByUsername(username)).thenReturn(Optional.of(fixture));

        assertThrows(
                SnsApplicationException.class,
                () -> userService.login(username, wrongPassword)
        );
    }
}