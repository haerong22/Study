package com.example.test_demo.post.controller.port;

import com.example.test_demo.post.domain.Post;
import com.example.test_demo.post.domain.PostCreate;
import com.example.test_demo.post.domain.PostUpdate;

public interface PostService {

    Post getById(long id);
    Post create(PostCreate postCreate);
    Post update(long id, PostUpdate postUpdate);
}
