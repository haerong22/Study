package com.example.test_demo.controller;

import com.example.test_demo.model.UserStatus;
import com.example.test_demo.model.dto.UserUpdateDto;
import com.example.test_demo.repository.UserEntity;
import com.example.test_demo.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@SqlGroup({
        @Sql(value = "/sql/user-controller-test-data.sql", executionPhase = BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-all-data.sql", executionPhase = AFTER_TEST_METHOD)
})
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("특정 유저의 개인정보를 제외한 정보를 응답받을 수 있다.")
    void getUserById_200() throws Exception {
        mockMvc
                .perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("test@test.com"))
                .andExpect(jsonPath("$.nickname").value("bobby"))
                .andExpect(jsonPath("$.address").doesNotExist())
                .andExpect(jsonPath("$.status").value("ACTIVE"))
        ;
    }

    @Test
    @DisplayName("존재하지 않는 유저의 아이디로 호출 시 404 응답 받는다.")
    void getUserById_404() throws Exception {
        mockMvc
                .perform(get("/api/users/1234"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Users에서 ID 1234를 찾을 수 없습니다."))
        ;
    }

    @Test
    @DisplayName("인증 코드로 계정을 활성화할 수 있다.")
    void verifyEmail_302() throws Exception {
        mockMvc
                .perform(
                        get("/api/users/2/verify")
                                .queryParam("certificationCode", "4321-1234-1234-1234")
                )
                .andExpect(status().isFound())
        ;

        UserEntity userEntity = userRepository.findById(2L).get();

        assertThat(userEntity.getStatus()).isEqualTo(UserStatus.ACTIVE);
    }

    @Test
    @DisplayName("내 정보를 불러올 때 주소를 받을 수 있다.")
    void getMyInfo_200() throws Exception {
        mockMvc
                .perform(
                        get("/api/users/me")
                                .header("EMAIL", "test@test.com")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("test@test.com"))
                .andExpect(jsonPath("$.nickname").value("bobby"))
                .andExpect(jsonPath("$.address").value("seoul"))
                .andExpect(jsonPath("$.status").value("ACTIVE"))
        ;
    }

    @Test
    @DisplayName("내 정보를 수정할 수 있다.")
    void updateMyInfo_200() throws Exception {
        UserUpdateDto userUpdateDto = UserUpdateDto.builder()
                .nickname("new bobby")
                .address("busan")
                .build();

        mockMvc
                .perform(
                        put("/api/users/me")
                                .header("EMAIL", "test@test.com")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userUpdateDto))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("test@test.com"))
                .andExpect(jsonPath("$.nickname").value("new bobby"))
                .andExpect(jsonPath("$.address").value("busan"))
                .andExpect(jsonPath("$.status").value("ACTIVE"))
        ;
    }
}