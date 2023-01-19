package com.example.board.service;

import com.example.board.domain.Hashtag;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class HashtagService {
    public Set<String> parseHashtagNames(String content) {
        return null;
    }

    public Set<Hashtag> findHashtagsByNames(Set<String> expectedHashtagNames) {
        return null;
    }

    public void deleteHashtagWithoutArticles(Object any) {
    }
}