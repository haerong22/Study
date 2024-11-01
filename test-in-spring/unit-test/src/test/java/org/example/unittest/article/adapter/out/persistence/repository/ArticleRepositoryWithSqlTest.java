package org.example.unittest.article.adapter.out.persistence.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.BDDAssertions.then;

@DataJpaTest
@Sql("/data/ArticleRepositoryFixtureTest.sql")
class ArticleRepositoryWithSqlTest {
    @Autowired
    private ArticleRepository repository;

    @Test
    void listAllArticles() {
        var result = repository.findByBoardId(5L);

        then(result)
            .hasSize(2);
    }

    @Test
    @Sql("/data/ArticleRepositoryFixtureTest.listAllArticles.sql")
    void listAllArticles2() {
        var result = repository.findByBoardId(5L);

        then(result)
            .hasSize(3);
    }
}