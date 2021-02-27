package com.example.apptest.study;

import com.example.apptest.domain.Member;
import com.example.apptest.domain.Study;
import com.example.apptest.member.MemberService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
@Testcontainers
public class StudyServiceSpringBootTest {

    @Mock MemberService memberService;
    @Autowired StudyRepository studyRepository;

    @Container
    static PostgreSQLContainer<?> postgreSQLContainer =
            new PostgreSQLContainer<>().withDatabaseName("studytest");

    @BeforeEach
    void beforeEach() {
        studyRepository.deleteAll();
    }

    @Test
    void test_51() {
        System.out.println("테스트 완료!!");
    }
}
