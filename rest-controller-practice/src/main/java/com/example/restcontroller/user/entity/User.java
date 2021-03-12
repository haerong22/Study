package com.example.restcontroller.user.entity;

import com.example.restcontroller.board.entity.Board;
import com.example.restcontroller.board.entity.BoardHits;
import com.example.restcontroller.board.entity.BoardLike;
import com.example.restcontroller.board.entity.BoardType;
import com.example.restcontroller.notice.entity.Notice;
import com.example.restcontroller.notice.entity.NoticeLike;
import com.example.restcontroller.user.model.UserInput;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String phone;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Column
    private boolean lockYn;

    private LocalDateTime regDate;
    private LocalDateTime updateDate;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    List<Notice> noticeList = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    List<NoticeLike> noticeLikeList = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    List<Board> boardList = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    List<BoardHits> boardHitsList = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    List<BoardLike> boardLikeList = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        status = status == null ? UserStatus.USING : status;
    }

    public User(UserInput userInput) {
        email = userInput.getEmail();
        userName = userInput.getUserName();
        password = userInput.getPassword();
        phone = userInput.getPhone();
        regDate = LocalDateTime.now();
    }

    public static User updateUser(User user, String phone) {
        user.phone = phone;
        user.updateDate = LocalDateTime.now();
        return user;
    }

    public String changeUserStatus() {
        status = status.equals(UserStatus.USING) ? UserStatus.STOP : UserStatus.USING;
        return status.toString();
    }

}
