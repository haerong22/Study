package com.example.jpablog.test;

import com.example.jpablog.model.RoleType;
import com.example.jpablog.model.User;
import com.example.jpablog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DummyControllerTest {

    private final UserRepository userRepository;

    // save : id 전달 X -> insert
    //        id 전달 -> id 있으면 update
    //               -> id 없으면 insert
    @Transactional
    @PutMapping("/dummy/user/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User requestUser) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("수정실패"));
        user
                .setPassword(requestUser.getPassword())
                .setEmail(requestUser.getEmail());
        return null;
    }

    @GetMapping("/dummy/user")
    public List<User> list() {
        return userRepository.findAll();
    }

    @GetMapping("/dummy/user/search")
    public List<User> pageList(
            @PageableDefault(size = 2,sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<User> pagingUsers = userRepository.findAll(pageable);
        List<User> users = pagingUsers.getContent();
        return users;
    }

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
