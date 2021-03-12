package com.example.restcontroller.notice.entity;

import com.example.restcontroller.user.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
@Data
@Entity
public class Notice {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "notice")
    List<NoticeLike> noticeLikeList = new ArrayList<>();

    private String title;
    private String contents;

    private LocalDateTime regDate;
    private LocalDateTime updateDate;
    private LocalDateTime deleteDate;

    @ColumnDefault("false")
    private boolean deleted;

    @ColumnDefault("0")
    private Integer hits;

    @ColumnDefault("0")
    private Integer likes;

    @PrePersist
    private void prePersist() {
        hits = hits == null ? 0 : hits;
        likes = likes == null ? 0 : likes;
    }
}
