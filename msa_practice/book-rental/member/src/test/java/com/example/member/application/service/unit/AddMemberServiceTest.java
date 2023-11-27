package com.example.member.application.service.unit;

import com.example.member.application.port.in.command.AddMemberCommand;
import com.example.member.application.port.out.MemberPort;
import com.example.member.application.service.AddMemberService;
import com.example.member.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddMemberServiceTest {

    @Mock
    private MemberPort memberPort;

    @InjectMocks
    private AddMemberService addMemberService;

    @Test
    @DisplayName("회원을 등록할 수 있다.")
    void addMember() {
        // given
        AddMemberCommand command = AddMemberCommand.builder()
                .build();

        when(memberPort.save(any(Member.class))).thenReturn(Member.builder().build());

        // when
        addMemberService.addMember(command);

        // then
        verify(memberPort, times(1)).save(any(Member.class));
    }
}