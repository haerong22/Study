package org.example.elsgraphql.service.dummy;

import org.example.elsgraphql.model.User;
import org.example.elsgraphql.service.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class DummyUserService implements UserService {

    private final List<User> users = new ArrayList<>();

    private final AtomicLong counter = new AtomicLong(100);

    public DummyUserService() {
        initData();
    }

    private void initData() {
        // Adding dummy users
        users.add(new User(counter.getAndIncrement(), "John Doe", "john.doe@example.com", "password123"));
        users.add(new User(counter.getAndIncrement(), "Jane Smith", "jane.smith@example.com", "password456"));
        users.add(new User(counter.getAndIncrement(), "Alice Johnson", "alice.johnson@example.com", "password789"));
    }

    public List<User> findAll() {
        return new ArrayList<>(users);
    }

    @Override
    public User createUser(String name, String email, String password) {
        User user = new User();
        if (user.getId() == null) {
            user.setId(counter.getAndIncrement());
        }
        user.setName(name);
        user.setEmail(email);
        user.setPasswordHash(password);

        users.add(user);
        return user;
    }

    @Override
    public Optional<User> findById(Long id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst();
    }

    @Override
    public User updateUser(Long id, String name, String email) {
        User user = findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setName(name);
        user.setEmail(email);
        return user;
    }

    public void deleteUser(Long id) {
        users.removeIf(user -> user.getId().equals(id));
    }
}