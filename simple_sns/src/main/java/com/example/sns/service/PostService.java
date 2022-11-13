package com.example.sns.service;

import com.example.sns.exception.SnsApplicationException;
import com.example.sns.model.Post;
import com.example.sns.model.entity.PostEntity;
import com.example.sns.model.entity.UserEntity;
import com.example.sns.repository.PostEntityRepository;
import com.example.sns.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.sns.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostEntityRepository postEntityRepository;
    private final UserEntityRepository userEntityRepository;

    @Transactional
    public void create(String title, String body, String username) {
        UserEntity userEntity = userEntityRepository.findByUsername(username)
                .orElseThrow(
                        () -> new SnsApplicationException(
                                USER_NOT_FOUND,
                                String.format("%s not founded", username)
                        )
                );

        postEntityRepository.save(PostEntity.of(title, body, userEntity));
    }

    @Transactional
    public Post modify(String title, String body, String username, Integer postId) {
        UserEntity userEntity = userEntityRepository.findByUsername(username)
                .orElseThrow(
                        () -> new SnsApplicationException(
                                USER_NOT_FOUND,
                                String.format("%s not founded", username)
                        )
                );

        PostEntity postEntity = postEntityRepository.findById(postId)
                .orElseThrow(
                        () -> new SnsApplicationException(
                                POST_NOT_FOUND,
                                String.format("%s not founded", postId)
                        )
                );

        if (postEntity.getUser() != userEntity) {
            throw new SnsApplicationException(
                    INVALID_PERMISSION,
                    String.format("%s has no permission with %s", username, postId)
            );
        }

        postEntity.setTitle(title);
        postEntity.setBody(body);

        return Post.fromEntity(postEntityRepository.saveAndFlush(postEntity));
    }

    @Transactional
    public void delete(String username, Integer postId) {
        UserEntity userEntity = userEntityRepository.findByUsername(username)
                .orElseThrow(
                        () -> new SnsApplicationException(
                                USER_NOT_FOUND,
                                String.format("%s not founded", username)
                        )
                );

        PostEntity postEntity = postEntityRepository.findById(postId)
                .orElseThrow(
                        () -> new SnsApplicationException(
                                POST_NOT_FOUND,
                                String.format("%s not founded", postId)
                        )
                );

        if (postEntity.getUser() != userEntity) {
            throw new SnsApplicationException(
                    INVALID_PERMISSION,
                    String.format("%s has no permission with %s", username, postId)
            );
        }

        postEntityRepository.delete(postEntity);
    }
}
