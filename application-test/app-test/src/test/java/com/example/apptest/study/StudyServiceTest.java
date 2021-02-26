package com.example.apptest.study;

import com.example.apptest.member.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class StudyServiceTest {

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

        MemberService memberService = Mockito.mock(MemberService.class);
        StudyRepository studyRepository = Mockito.mock(StudyRepository.class);
        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);
        System.out.println("테스트 완료!");
    }

}