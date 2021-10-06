package com.example.jwt.repository;

import com.example.jwt.entity.LoginReq;
import com.example.jwt.entity.Member;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TestRepository {

    private final List<Member> members = new ArrayList<>();
    private static int sequence = 0;

    public TestRepository() {
        members.add(new Member(++sequence, "kim", "1234"));
    }

    public boolean login(LoginReq req) {
         return members.stream()
                .anyMatch(m -> m.getUsername().equals(req.getUsername()) && m.getPassword().equals(req.getPassword()));
    }
}
