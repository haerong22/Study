package com.example.restcontroller.board.entity;

import com.example.restcontroller.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class BoardComment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_BOARD_COMMENT_USER_ID"))
    private User user;

    @ManyToOne()
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_BOARD_COMMENT_BOARD_ID"))
    private Board board;

    private String comments;

    private LocalDateTime regDate;
}
