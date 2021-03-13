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
public class BoardBookmark {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_BOARD_BOOKMARK_USER_ID"))
    private User user;

    // 북마크 정보
    private Long boardId;
    private Long boardTypeId;
    private String boardTitle;
    private String boardUrl;

    private LocalDateTime regDate;
}
