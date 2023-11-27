package com.example.member.domain;

import com.example.member.domain.vo.*;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class Member {

    private Long memberNo;
    private IDName idName;
    private Password password;
    private Email email;
    private List<Authority> authorites;
    private Point point;

    public static Member register(IDName idName, Password pwd, Email email) {
        return Member.builder()
                .idName(idName)
                .password(pwd)
                .email(email)
                .authorites(new ArrayList<>() {{
                    add(Authority.create(UserRole.USER));
                }})
                .point(Point.create(0))
                .build();
    }

    public void savePoint(long point) {
        this.point = this.point.addPoint(point);
    }

    public void usePoint(long point) {
        this.point = this.point.removePoint(point);
    }
}
