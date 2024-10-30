package org.example.unittest.article.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.unittest.article.domain.Article;

import java.time.LocalDateTime;

@Entity
@Table(name = "article")
@NoArgsConstructor
@Getter
public class ArticleJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private BoardJpaEntity board;

    @Column(nullable = false)
    private String subject;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public ArticleJpaEntity(BoardJpaEntity board, String subject, String content, String username, LocalDateTime createdAt) {
        this.board = board;
        this.subject = subject;
        this.content = content;
        this.username = username;
        this.createdAt = createdAt;
    }

    private ArticleJpaEntity(Long id, BoardJpaEntity board, String subject, String content, String username, LocalDateTime createdAt) {
        this.id = id;
        this.board = board;
        this.subject = subject;
        this.content = content;
        this.username = username;
        this.createdAt = createdAt;
    }

    public Article toDomain() {
        return Article.builder()
            .id(this.id)
            .board(this.board.toDomain())
            .subject(this.subject)
            .content(this.content)
            .username(this.username)
            .createdAt(this.createdAt)
            .build();
    }

    public static ArticleJpaEntity fromDomain(Article article) {
        return new ArticleJpaEntity(
            article.getId(),
            BoardJpaEntity.fromDomain(article.getBoard()),
            article.getSubject(),
            article.getContent(),
            article.getUsername(),
            article.getCreatedAt()
        );
    }
}