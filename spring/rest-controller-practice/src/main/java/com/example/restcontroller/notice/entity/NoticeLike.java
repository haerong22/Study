package com.example.restcontroller.notice.entity;

import com.example.restcontroller.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
@Data
@Entity
public class NoticeLike {

    //ID, 제목, 내용, 등록일(작성일)
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Notice notice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

}
