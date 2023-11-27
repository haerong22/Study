package com.example.member.application.service.unit;

import com.example.member.application.port.out.MemberPort;
import com.example.member.application.service.InquiryMemberService;
import com.example.member.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InquiryMemberServiceTest {

    @Mock
    private MemberPort memberPort;

    @InjectMocks
    private InquiryMemberService inquiryMemberService;

    @Test
    @DisplayName("회원을 조회할 수 있다.")
    void getMember() {
        // given
        long memberNo = 1;

        when(memberPort.getMember(anyLong())).thenReturn(Member.builder().build());

        // when
        inquiryMemberService.getMember(memberNo);

        // then
        verify(memberPort, times(1)).getMember(anyLong());
    }

}