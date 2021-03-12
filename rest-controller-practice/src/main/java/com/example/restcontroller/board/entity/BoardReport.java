package com.example.restcontroller.board.entity;

import com.example.restcontroller.user.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class BoardReport {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 신고자
    private Long userId;
    private String userName;
    private String userEmail;

    // 신고 게시글
    private Long boardId;
    private Long boardUserId;
    private String boardTitle;
    private String boardContents;
    private LocalDateTime boardRegDate;

    // 신고내용
    private String comments;
    private LocalDateTime regDate;
}
