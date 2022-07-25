package com.example.simpleblog.controller;

import com.example.simpleblog.request.PostCreate;
import com.example.simpleblog.response.PostResponse;
import com.example.simpleblog.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/posts")
    public void post(@RequestBody @Valid PostCreate request) {
        postService.write(request);
    }

    @GetMapping("/posts/{postId}")
    public PostResponse getPost(@PathVariable(name = "postId") Long id) {
        return postService.getPost(id);
    }

    @GetMapping("/posts")
    public List<PostResponse> getPostList() {
        return postService.getPostList();
    }
}
