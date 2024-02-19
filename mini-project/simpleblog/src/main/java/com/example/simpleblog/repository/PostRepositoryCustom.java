package com.example.simpleblog.repository;

import com.example.simpleblog.domain.Post;
import com.example.simpleblog.request.PostSearch;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> getList(PostSearch postSearch);
}
