package com.example.book.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

// 단위테스트 ( DB 관련 Bean 만 IoC에 등록되면 됨 )

@Transactional
@AutoConfigureTestDatabase(replace = Replace.ANY) // Replace.ANY 가짜 DB로 테스트, Replace.NONE 실제 DB로 테스트
@DataJpaTest // Repository 들을 spring container 에 등록해줌
class BookRepositoryUnitTest {

    @Autowired
    private BookRepository bookRepository;

}