package com.example.test_demo.mock;

import com.example.test_demo.common.service.port.ClockHolder;
import com.example.test_demo.common.service.port.UuidHolder;
import com.example.test_demo.post.controller.PostController;
import com.example.test_demo.post.controller.PostCreateController;
import com.example.test_demo.post.controller.port.PostService;
import com.example.test_demo.post.service.PostServiceImpl;
import com.example.test_demo.post.service.port.PostRepository;
import com.example.test_demo.user.controller.UserController;
import com.example.test_demo.user.controller.UserCreateController;
import com.example.test_demo.user.controller.port.UserService;
import com.example.test_demo.user.service.CertificationService;
import com.example.test_demo.user.service.UserServiceImpl;
import com.example.test_demo.user.service.port.MailSender;
import com.example.test_demo.user.service.port.UserRepository;
import lombok.Builder;

public class TestContainer {

    public final MailSender mailSender;
    public final UserRepository userRepository;
    public final PostRepository postRepository;
    public final CertificationService certificationService;
    public final UserService userService;
    public final PostService postService;
    public final UserController userController;
    public final UserCreateController userCreateController;
    public final PostController postController;
    public final PostCreateController postCreateController;

    @Builder
    public TestContainer(ClockHolder clockHolder, UuidHolder uuidHolder) {
        this.mailSender = new FakeMailSender();
        this.userRepository = new FakeUserRepository();
        this.postRepository = new FakePostRepository();

        this.certificationService = new CertificationService(this.mailSender);

        this.userService = UserServiceImpl.builder()
                .userRepository(this.userRepository)
                .certificationService(this.certificationService)
                .uuidHolder(uuidHolder)
                .clockHolder(clockHolder)
                .build();

        this.postService = PostServiceImpl.builder()
                .postRepository(this.postRepository)
                .userRepository(this.userRepository)
                .clockHolder(clockHolder)
                .build();

        this.userController = UserController.builder()
                .userService(this.userService)
                .build();

        this.userCreateController = UserCreateController.builder()
                .userService(this.userService)
                .build();

        this.postController = PostController.builder()
                .postService(this.postService)
                .build();

        this.postCreateController = PostCreateController.builder()
                .postService(this.postService)
                .build();
    }

}
