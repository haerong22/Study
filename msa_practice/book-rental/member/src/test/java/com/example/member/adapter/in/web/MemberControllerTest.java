package com.example.member.adapter.in.web;

import com.example.member.application.port.in.AddMemberUseCase;
import com.example.member.application.port.in.InquiryMemberUseCase;
import com.example.member.application.port.in.command.AddMemberCommand;
import com.example.member.domain.Member;
import com.example.member.domain.vo.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MemberController.class)
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private AddMemberUseCase addMemberUseCase;

    @MockBean
    private InquiryMemberUseCase inquiryMemberUseCase;

    @Test
    @DisplayName("회원을 등록한다.")
    void addMember() throws Exception {
        // given
        AddMemberCommand request = AddMemberCommand.builder()
                .build();

        when(addMemberUseCase.addMember(any(AddMemberCommand.class))).thenReturn(createTestMember());

        // when

        // then
        mockMvc.perform(
                        post("/api/v1/member")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andDo(print())
                .andExpect(status().isCreated())
        ;

        verify(addMemberUseCase).addMember(any(AddMemberCommand.class));
    }

    @Test
    @DisplayName("회원을 조회한다.")
    void getMember() throws Exception {
        // given
        when(inquiryMemberUseCase.getMember(anyLong())).thenReturn(createTestMember());

        // when

        // then
        mockMvc.perform(
                        get("/api/v1/member/1")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
        ;

        verify(inquiryMemberUseCase).getMember(anyLong());
    }

    private Member createTestMember() {
        return Member.builder()
                .idName(IDName.create("id", "name"))
                .password(Password.create("presentPwd", "pastPwd"))
                .email(Email.create("test@test.com"))
                .authorites(new ArrayList<>() {{
                    add(Authority.create(UserRole.USER));
                }})
                .point(Point.create(0))
                .build();
    }
}