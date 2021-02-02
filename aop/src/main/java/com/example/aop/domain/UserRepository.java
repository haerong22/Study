package com.example.aop.domain;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {

    List<User> users = new ArrayList<>();
    public UserRepository () {
        users.add(new User(1, "user1", "1234", "1234"));
        users.add(new User(2, "user2", "1234", "1234"));
        users.add(new User(3, "user3", "1234", "1234"));
    }

    public List<User> findAll() {
        return users;
    }

    public User findById(int id) {
        return users.stream().filter(v -> v.getId() == id).findAny().orElseThrow();
    }

    public void save(JoinReqDto dto) {
        User user = User.builder()
                .id(users.size()+1)
                .username(dto.getUsername())
                .password(dto.getPassword())
                .phone(dto.getPhone())
                .build();
        users.add(user);
        System.out.println("DB 저장");
    }

    public int delete(int id) {
        for (User v : users) {
            if (v.getId() == id) {
                users.remove(v);
                return 1;
            }
        }
        return 0;
    }

    public void update(int id, UpdateReqDto dto) {
//        throw new IllegalArgumentException("오류");
        users.forEach(v -> {
            if (v.getId() == id) {
                v.setUsername(dto.getUsername());
                v.setPhone(dto.getPhone());
            }
        });
    }
}
