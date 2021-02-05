package com.example.book.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * 통합테스트 ( 모든 Bean 들을 IoC에 올리고 테스트 하는 것 )
 * WebEnvironment.MOCK = 실제 톰캣이 아닌 다른 톰캣으로 테스트
 * WebEnvironment.RANDOM_PORT = 실제 톰캣으로 테스트
 * @AutoConfigureMockMvc = MockMvc 를 IoC에 등록
 * @Transactional = 각 테스트 함수가 종료되면 트랜잭션을 rollback
 *
 */

@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class BookApplicationIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

}
