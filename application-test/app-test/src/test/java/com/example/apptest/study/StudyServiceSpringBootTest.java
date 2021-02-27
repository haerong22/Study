package com.example.apptest.study;

import com.example.apptest.domain.Member;
import com.example.apptest.domain.Study;
import com.example.apptest.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.wait.strategy.Wait;
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
@Slf4j
public class StudyServiceSpringBootTest {

    @Mock MemberService memberService;
    @Autowired StudyRepository studyRepository;

    @Container
    static GenericContainer<?> postgreSQLContainer =
            new GenericContainer<>("postgres")
                    .withEnv("POSTGRES_DB", "studytest");

    @BeforeAll
    static void beforeAll() {
        Slf4jLogConsumer logConsumer = new Slf4jLogConsumer(log);
        postgreSQLContainer.followOutput(logConsumer);
    }
//    @Container
//    static PostgreSQLContainer<?> postgreSQLContainer =
//            new PostgreSQLContainer<>().withDatabaseName("studytest");

    @BeforeEach
    void beforeEach() {
        System.out.println("==========================");
        System.out.println(postgreSQLContainer.getMappedPort(5432));
        System.out.println("==========================");
        System.out.println(postgreSQLContainer.getLogs());
        studyRepository.deleteAll();
    }

    @Test
    void test_51() {
        System.out.println("테스트 완료!!");
    }
}
