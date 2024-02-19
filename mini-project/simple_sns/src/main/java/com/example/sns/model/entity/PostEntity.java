package com.example.sns.model.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "posts")
@Getter
@Setter
@SQLDelete(sql = "UPDATE posts SET deleted_at = NOW() where id=?")
@Where(clause = "deleted_at IS NULL")
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String body;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    private Timestamp registeredAt;
    private Timestamp updatedAt;

    @Column(name = "deleted_at")
    private Timestamp deletedAt;

    @PrePersist
    void registeredAt() {
        this.registeredAt = Timestamp.from(Instant.now());
    }

    @PreUpdate
    void updatedAt() {
        this.updatedAt = Timestamp.from(Instant.now());
    }

    public static PostEntity of(String title, String body, UserEntity userEntity) {
        PostEntity entity = new PostEntity();
        entity.setTitle(title);
        entity.setBody(body);
        entity.setUser(userEntity);
        return entity;
    }
}
