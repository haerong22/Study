package com.example.restcontroller.user.entity;

import com.example.restcontroller.user.model.UserPointType;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class UserPoint {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_USER_POINT_USER_ID"))
    private User user;

    private int point;

    @Enumerated(EnumType.STRING)
    private UserPointType userPointType;

    private LocalDateTime regDate;

}
