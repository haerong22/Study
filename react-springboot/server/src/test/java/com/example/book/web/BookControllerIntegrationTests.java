package com.example.book.web;

import com.example.book.domain.Book;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 통합테스트 ( 모든 Bean 들을 IoC에 올리고 테스트 하는 것 )
 * WebEnvironment.MOCK = 실제 톰캣이 아닌 다른 톰캣으로 테스트
 * WebEnvironment.RANDOM_PORT = 실제 톰캣으로 테스트
 * @AutoConfigureMockMvc = MockMvc 를 IoC에 등록
 * @Transactional = 각 테스트 함수가 종료되면 트랜잭션을 rollback
 *
 */

@Slf4j
@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class BookControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    // BDDMockito 패턴 given, when, then
    @Test
    public void save_테스트() throws Exception {
        log.info("save 테스트 시작 ==================================");
        // given ( 테스트를 하기 위한 준비 )
        Book book = new Book(null, "스프링", "kim");
        String content = new ObjectMapper().writeValueAsString(book);

        // when ( 테스트 실행 )
        ResultActions resultActions = mockMvc.perform(post("/book")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .accept(MediaType.APPLICATION_JSON_UTF8));

        // then ( 검증 )
        resultActions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("스프링"))
                .andDo(print());
    }
}
