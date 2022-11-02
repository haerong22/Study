package com.example.sns.service;

import com.example.sns.exception.ErrorCode;
import com.example.sns.exception.SnsApplicationException;
import com.example.sns.model.entity.PostEntity;
import com.example.sns.model.entity.UserEntity;
import com.example.sns.repository.PostEntityRepository;
import com.example.sns.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostEntityRepository postEntityRepository;
    private final UserEntityRepository userEntityRepository;

    @Transactional
    public void create(String title, String body, String username) {

        // TODO user find
        UserEntity userEntity = userEntityRepository.findByUsername(username)
                .orElseThrow(
                        () -> new SnsApplicationException(
                                ErrorCode.USER_NOT_FOUND,
                                String.format("%s not founded", username)
                        )
                );

        // TODO post save
        postEntityRepository.save(new PostEntity());

        // TODO return
    }

}
