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
public class Board {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn
    private User user;

    @ManyToOne
    @JoinColumn
    private BoardType boardType;

    @JsonIgnore
    @OneToMany(mappedBy = "board")
    List<BoardHits> boardHitsList = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "board")
    List<BoardLike> boardLikeList = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "board")
    List<BoardComment> boardCommentList  = new ArrayList<>();

    private String title;

    private String content;

    private boolean topYn;

    private LocalDateTime regDate;

    private LocalDate publishStartDate;
    private LocalDate publishEndDate;

}
