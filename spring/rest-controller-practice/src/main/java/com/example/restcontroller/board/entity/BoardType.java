package com.example.restcontroller.board.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class BoardType {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String boardName;

    private LocalDateTime regDate;

    private LocalDateTime updateDate;

    private boolean usingYn;

    @JsonIgnore
    @OneToMany(mappedBy = "boardType")
    List<Board> boardList = new ArrayList<>();

    @Override
    public String toString() {
        return "BoardType{" +
                "id=" + id +
                ", boardName='" + boardName + '\'' +
                ", regDate=" + regDate +
                ", updateDate=" + updateDate +
                ", usingYn=" + usingYn +
                '}';
    }
}
