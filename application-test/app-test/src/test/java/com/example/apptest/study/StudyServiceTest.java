package com.example.apptest.study;

import com.example.apptest.domain.Member;
import com.example.apptest.domain.Study;
import com.example.apptest.member.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudyServiceTest {

    @Test
    @DisplayName("BDD style test")
    void test_49(@Mock MemberService memberService,
                 @Mock StudyRepository studyRepository) {

        // Given : 주어진 상황
        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);

        Study study = new Study(10, "테스트");
        Member member = new Member();
        member.setId(1L);
        member.setEmail("email@email.com");

        // when -> given ( BDDMockito )
        given(memberService.findById(1L)).willReturn(Optional.of(member));
        given(studyRepository.save(study)).willReturn(study);

        // When : 어떤 행동을 했을 때
        Study newStudy = studyService.createNewStudy(1L, study);

        // Then : 그 결과
        assertEquals(member.getId(), newStudy.getOwnerId());

        // verify -> then
        then(memberService).should(times(1)).notify(newStudy);
        then(memberService).should(times(1)).notify(member);
        then(memberService).shouldHaveNoMoreInteractions();

        System.out.println("테스트 완료!!");
    }

    @Test
    @DisplayName("stubbing test")
    void test_48(@Mock MemberService memberService,
                 @Mock StudyRepository studyRepository) {

        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);

        Study study = new Study(10, "테스트");
        Member member = new Member();
        member.setId(1L);
        member.setEmail("email@email.com");

        when(memberService.findById(1L)).thenReturn(Optional.of(member));
        when(studyRepository.save(study)).thenReturn(study);

        Study newStudy = studyService.createNewStudy(1L, study);

        // 특정 시점 이후에 실행 되었는지 확인
        verify(memberService, times(1)).notify(newStudy);
        verify(memberService, times(1)).notify(member);
        verifyNoMoreInteractions(memberService);

        System.out.println("테스트 완료!!");
    }

    @Test
    @DisplayName("stubbing test")
    void test_47(@Mock MemberService memberService,
                 @Mock StudyRepository studyRepository) {

        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);

        Study study = new Study(10, "테스트");
        Member member = new Member();
        member.setId(1L);
        member.setEmail("email@email.com");

        when(memberService.findById(1L)).thenReturn(Optional.of(member));
        when(studyRepository.save(study)).thenReturn(study);

        studyService.createNewStudy(1L, study);

        // 메소드 실행 순서 확인
        InOrder inOrder = inOrder(memberService);
        inOrder.verify(memberService).notify(study);
        inOrder.verify(memberService).notify(member);

        System.out.println("테스트 완료!!");
    }

    @Test
    @DisplayName("stubbing test")
    void test_46(@Mock MemberService memberService,
                 @Mock StudyRepository studyRepository) {

        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);

        Study study = new Study(10, "테스트");
        Member member = new Member();
        member.setId(1L);
        member.setEmail("email@email.com");

        when(memberService.findById(1L)).thenReturn(Optional.of(member));
        when(studyRepository.save(study)).thenReturn(study);

        studyService.createNewStudy(1L, study);

        // 특정 메소드의 호출 횟수 테스트
        // memberService.notify() 1번 호출
        verify(memberService, times(1)).notify(study);
        verify(memberService, times(1)).notify(member);
        // memberService.validate() 호출 X
        verify(memberService, never()).validate(any());

        System.out.println("테스트 완료!!");
    }

    @Test
    @DisplayName("stubbing test")
    void test_45(@Mock MemberService memberService,
                 @Mock StudyRepository studyRepository) {

        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);

        Study study = new Study(10, "테스트");
        Member member = new Member();
        member.setId(1L);
        member.setEmail("email@email.com");

        when(memberService.findById(1L))
                .thenReturn(Optional.of(member));
        when(studyRepository.save(study)).thenReturn(study);

        studyService.createNewStudy(1L, study);

        assertNotNull(study.getOwnerId());
        assertEquals(member.getId(), study.getOwnerId());

        System.out.println("테스트 완료!!");
    }

    @Test
    @DisplayName("Stubbing test")
    void test_44(@Mock MemberService memberService,
                 @Mock StudyRepository studyRepository) {

        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);

        // 리턴할 객체 생성
        Member member = new Member();
        member.setId(1L);
        member.setEmail("email@email.com");

        // 첫번째 호출 Optional.of(member) 리턴
        // 두번째 호출 new RuntimeException() 리턴
        // 세번째 호출 이후 Optional.empty() 리턴
        when(memberService.findById(any()))
                .thenReturn(Optional.of(member))
                .thenThrow(new RuntimeException())
                .thenReturn(Optional.empty());

        // 첫번째 호출
        Optional<Member> findMember = memberService.findById(1L);
        assertEquals("email@email.com", findMember.get().getEmail());

        // 두번째 호출
        assertThrows(RuntimeException.class, () -> memberService.findById(1L));

        // 세번째 호출
        assertEquals(Optional.empty(), memberService.findById(1L));

        // 네번째 호출
        assertEquals(Optional.empty(), memberService.findById(1L));

        System.out.println("테스트 완료!");
    }

    @Test
    @DisplayName("Stubbing test")
    void test_43(@Mock MemberService memberService,
                 @Mock StudyRepository studyRepository) {

        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);

        // 리턴할 객체 생성
        Member member = new Member();
        member.setId(1L);
        member.setEmail("email@email.com");

        // 예외 테스트 -> memberService.validate(1L) 이 호출되면 IllegalArgumentException 발생
        doThrow(new IllegalArgumentException()).when(memberService).validate(1L);

        // 예외 체크
        assertThrows(IllegalArgumentException.class, () -> memberService.validate(1L));

        memberService.validate(2L);

        System.out.println("테스트 완료!");
    }

    @Test
    @DisplayName("Stubbing test")
    void test_42(@Mock MemberService memberService,
                 @Mock StudyRepository studyRepository) {

        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);

        // 리턴할 객체 생성
        Member member = new Member();
        member.setId(1L);
        member.setEmail("email@email.com");

        // when( 실행할 구문 ) thenReturn( 리턴 값 )
        // memberService.findById( 아무값 ) 이 호출되면 Optional.of(member) 리턴
        when(memberService.findById(any())).thenReturn(Optional.of(member));

        assertEquals("email@email.com", memberService.findById(1L).get().getEmail());
        assertEquals("email@email.com", memberService.findById(2L).get().getEmail());

        System.out.println("테스트 완료!");
    }

    @Test
    @DisplayName("@Mock test")
    void test_41(@Mock MemberService memberService,
                 @Mock StudyRepository studyRepository) {
        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);
        System.out.println("테스트 완료!");
    }

//    @Mock MemberService memberService;
//    @Mock StudyRepository studyRepository;
//
//    @Test
//    @DisplayName("@Mock test")
//    void test_40() {
//        StudyService studyService = new StudyService(memberService, studyRepository);
//        assertNotNull(studyService);
//        System.out.println("테스트 완료!");
//    }

    @Test
    @DisplayName("Mockito.mock() test")
    void test_39() {

        MemberService memberService = mock(MemberService.class);
        StudyRepository studyRepository = mock(StudyRepository.class);
        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);
        System.out.println("테스트 완료!");
    }

}