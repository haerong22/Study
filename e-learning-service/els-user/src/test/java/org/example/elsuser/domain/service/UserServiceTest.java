package org.example.elsuser.domain.service;

import org.example.elsuser.domain.entity.User;
import org.example.elsuser.domain.repository.UserLoginHistoryRepository;
import org.example.elsuser.domain.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserLoginHistoryRepository userLoginHistoryRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void create_user() {
        // given
        String name = "John Doe";
        String email = "john@example.com";
        String password = "password123";

        User newUser = new User();
        newUser.setName(name);
        newUser.setEmail(email);

        given(passwordEncoder.encode(password)).willReturn("encodedPassword");
        given(userRepository.save(any(User.class))).willReturn(newUser);

        // when
        User savedUser = userService.createUser(name, email, password);

        // then
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getName()).isEqualTo(name);
        assertThat(savedUser.getEmail()).isEqualTo(email);
        verify(userRepository).save(any(User.class));
        verify(passwordEncoder).encode(password);
    }

    @Test
    void get_user_by_id_found() {
        // given
        Integer userId = 1;
        Optional<User> expectedUser = Optional.of(new User());
        given(userRepository.findById(userId)).willReturn(expectedUser);

        // when
        User result = userService.getUserById(userId);

        // then
        assertThat(result).isNotNull();
        verify(userRepository).findById(userId);
    }

    @Test
    void update_user_success() {
        // given
        Integer userId = 1;
        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setName("Original Name");
        existingUser.setEmail("original@example.com");

        given(userRepository.findById(userId)).willReturn(Optional.of(existingUser));
        given(userRepository.save(any(User.class))).willReturn(existingUser);

        // when
        User updatedUser = userService.updateUser(userId, "Updated Name", "updated@example.com");

        // then
        assertThat(updatedUser).isNotNull();
        assertThat(updatedUser.getName()).isEqualTo("Updated Name");
        assertThat(updatedUser.getEmail()).isEqualTo("updated@example.com");
        verify(userRepository).save(any(User.class));
    }

    @Test
    void change_password_success() {
        // given
        Integer userId = 1;
        String oldPassword = "oldPassword";
        String newPassword = "newPassword";
        User user = new User();
        user.setPasswordHash("encodedOldPassword");

        given(userRepository.findById(userId)).willReturn(Optional.of(user));
        given(passwordEncoder.matches(oldPassword, user.getPasswordHash())).willReturn(true);
        given(passwordEncoder.encode(newPassword)).willReturn("encodedNewPassword");
        given(userRepository.save(user)).willReturn(user);

        // when, then
        assertThatCode(() -> userService.changePassword(userId, oldPassword, newPassword))
                .doesNotThrowAnyException();
        verify(passwordEncoder).encode(newPassword);
        verify(userRepository).save(user);
    }

    @Test
    void change_password_fail_with_wrong_password() {
        // given
        Integer userId = 1;
        String oldPassword = "oldPassword";
        String newPassword = "newPassword";
        User user = new User();
        user.setPasswordHash("encodedOldPassword");

        given(userRepository.findById(userId)).willReturn(Optional.of(user));
        given(passwordEncoder.matches(oldPassword, user.getPasswordHash())).willReturn(false);

        // when, then
        assertThatThrownBy(() -> userService.changePassword(userId, oldPassword, newPassword))
                .isInstanceOf(IllegalArgumentException.class);

        verify(passwordEncoder, never()).encode(anyString());
        verify(userRepository, never()).save(any(User.class));
    }
}