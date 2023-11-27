package com.example.member.application.service.unit;

import com.example.member.application.port.out.MemberPort;
import com.example.member.application.service.SavePointService;
import com.example.member.domain.Member;
import com.example.member.domain.vo.IDName;
import com.example.member.domain.vo.Point;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SavePointServiceTest {

    @Mock
    private MemberPort memberPort;

    @InjectMocks
    private SavePointService savePointService;

    @Test
    @DisplayName("포인트를 적립할 수 있다.")
    void savePoint() {
        // given
        IDName idName = IDName.create("001", "bobby");
        long point = 100;

        Member member = createTestMember();
        when(memberPort.getMemberByIdName(any(IDName.class))).thenReturn(member);
        when(memberPort.save(any(Member.class))).thenReturn(Member.builder().build());

        // when
        savePointService.savePoint(idName, point);

        // then
        assertThat(member.getPoint().getPointValue()).isEqualTo(100);

        verify(memberPort, times(1)).getMemberByIdName(any(IDName.class));
        verify(memberPort, times(1)).save(any(Member.class));
    }

    private Member createTestMember() {
        return Member.builder()
                .point(Point.create(0))
                .build();
    }

}