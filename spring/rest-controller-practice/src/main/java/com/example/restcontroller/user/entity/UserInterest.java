package com.example.restcontroller.user.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class UserInterest {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_USER_INTEREST_USER_ID"))
    private User user;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "FK_USER_INTEREST_INTEREST_USER_ID"))
    private User interestUser;

    private LocalDateTime regDate;

}
