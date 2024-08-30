package org.example.todojavatokotlin.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Entity
@Table(name = "todos")
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "done")
    private Boolean done;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public void update(String title, String description, Boolean done) {
        this.title = title;
        this.description = description;
        this.done = done != null && done;
        this.updatedAt = LocalDateTime.now();
    }
}