package com.example.member.domain;

import com.example.member.domain.vo.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Member {

    private Long memberNo;
    private IDName idName;
    private Password password;
    private Email email;
    private List<Authority> authorites = new ArrayList<>();
    private Point point;
}
