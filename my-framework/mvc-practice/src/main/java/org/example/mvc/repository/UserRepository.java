package org.example.mvc.repository;

import org.example.mvc.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRepository {

    private final static Map<String, User> users = new HashMap<>();

    public static void save(User user) {
        users.put(user.getUserId(), user);
    }

    public static Collection<User> findAll() {
        return users.values();
    }
}
