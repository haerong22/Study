package com.userdemo.model.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table
@Entity
public class Writer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long writerId;

    private String name;

    private LocalDateTime createdAt;
}
