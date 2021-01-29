package com.example.jpablog.test;

import com.example.jpablog.model.RoleType;
import com.example.jpablog.model.User;
import com.example.jpablog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.function.Supplier;

@RestController
@RequiredArgsConstructor
public class DummyControllerTest {

    private final UserRepository userRepository;

    @GetMapping("/dummy/user/{id}")
    public User detail(@PathVariable Long id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("유저 없음. id : " + id));
    }

    @PostMapping("/dummy/join")
    public String join(@RequestBody User user) {
        user.setRole(RoleType.USER);
        userRepository.save(user);
        return "회원가입 완료";
    }

}
