package com.example.jpablog.test;

import com.example.jpablog.model.RoleType;
import com.example.jpablog.model.User;
import com.example.jpablog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DummyControllerTest {

    private final UserRepository userRepository;

    @PostMapping("/dummy/join")
    public String join(@RequestBody User user) {
        user.setRole(RoleType.USER);
        userRepository.save(user);
        return "회원가입 완료";
    }
}
