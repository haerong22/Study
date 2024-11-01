package org.example.unittest.article.adapter.out.persistence.repository;

import org.example.unittest.article.adapter.out.persistence.entity.ArticleJpaEntity;
import org.example.unittest.article.adapter.out.persistence.entity.BoardJpaEntity;
import org.example.unittest.article.domain.BoardType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;

import static org.assertj.core.api.BDDAssertions.then;

@DataJpaTest
class ArticleRepositoryTest {
    @Autowired
    private ArticleRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    private BoardJpaEntity boardJpaEntity;

    @BeforeEach
    void setUp() {
        boardJpaEntity = entityManager.persist(new BoardJpaEntity("test", BoardType.GENERAL));

        entityManager.persist(new ArticleJpaEntity(boardJpaEntity, "subject1", "content1", "user", LocalDateTime.now()));
        entityManager.persist(new ArticleJpaEntity(boardJpaEntity, "subject2", "content2", "user", LocalDateTime.now()));
    }

    @Test
    void listAllArticles() {
        var result = repository.findByBoardId(boardJpaEntity.getId());

        then(result)
                .hasSize(2);
    }
}