package org.example.splearn.domain.member;

import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.example.splearn.domain.AbstractEntity;

import java.time.LocalDateTime;

@Entity
@Getter
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberDetail extends AbstractEntity {

    private String profile;

    private String introduction;

    private LocalDateTime registeredAt;

    private LocalDateTime activatedAt;

    private LocalDateTime deactivatedAt;
}
