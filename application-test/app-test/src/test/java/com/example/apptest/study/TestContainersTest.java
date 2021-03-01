package com.example.apptest.study;

import com.example.apptest.member.MemberService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
@Testcontainers
//@Slf4j
@ContextConfiguration(initializers = TestContainersTest.ContainerPropertyInitializer.class)
public class TestContainersTest {

    @Mock MemberService memberService;
    @Autowired StudyRepository studyRepository;
//    @Autowired Environment environment;
    @Value("${container.port}") int port;

    @Container
    static DockerComposeContainer<?> composeContainer =
            new DockerComposeContainer<>(new File("src/test/resources/docker-compose.yml"))
            .withExposedService("study-db", 5432);

//    @Container
//    static GenericContainer<?> postgreSQLContainer =
//            new GenericContainer<>("postgres")
//                    .withEnv("POSTGRES_DB", "studytest");

//    @BeforeAll
//    static void beforeAll() {
//        Slf4jLogConsumer logConsumer = new Slf4jLogConsumer(log);
//        postgreSQLContainer.followOutput(logConsumer);
//    }
//    @Container
//    static PostgreSQLContainer<?> postgreSQLContainer =
//            new PostgreSQLContainer<>().withDatabaseName("studytest");

//    @BeforeEach
//    void beforeEach() {
//        System.out.println("==========================");
//        System.out.println(postgreSQLContainer.getMappedPort(5432));
//        System.out.println("==========================");
//        System.out.println(environment.getProperty("container.port"));
//        System.out.println("==========================");
//        System.out.println(port);
////        System.out.println(postgreSQLContainer.getLogs());
//        studyRepository.deleteAll();
//    }

    @Test
    void test_51() {
        System.out.println(port);
        System.out.println("==============");
        System.out.println("테스트 완료!!");
    }

    static class ContainerPropertyInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext context) {
            TestPropertyValues.of("container.port=" + composeContainer.getServicePort("study-db", 5432))
                    .applyTo(context.getEnvironment());
        }
    }

//    static class ContainerPropertyInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
//        @Override
//        public void initialize(ConfigurableApplicationContext context) {
//            TestPropertyValues.of("container.port=" + postgreSQLContainer.getMappedPort(5432))
//                    .applyTo(context.getEnvironment());
//        }
//    }
}
