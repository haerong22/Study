package com.example.sns.service;

import com.example.sns.exception.SnsApplicationException;
import com.example.sns.model.Post;
import com.example.sns.model.entity.LikeEntity;
import com.example.sns.model.entity.PostEntity;
import com.example.sns.model.entity.UserEntity;
import com.example.sns.repository.LikeEntityRepository;
import com.example.sns.repository.PostEntityRepository;
import com.example.sns.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.sns.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostEntityRepository postEntityRepository;
    private final UserEntityRepository userEntityRepository;
    private final LikeEntityRepository likeEntityRepository;

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

    public Page<Post> list(Pageable pageable) {
        return postEntityRepository.findAll(pageable).map(Post::fromEntity);
    }

    public Page<Post> my(String username, Pageable pageable) {
        UserEntity userEntity = userEntityRepository.findByUsername(username)
                .orElseThrow(
                        () -> new SnsApplicationException(
                                USER_NOT_FOUND,
                                String.format("%s not founded", username)
                        )
                );
        return postEntityRepository.findAllByUser(userEntity, pageable).map(Post::fromEntity);
    }

    @Transactional
    public void like(Integer postId, String username) {
        PostEntity postEntity = postEntityRepository.findById(postId)
                .orElseThrow(
                        () -> new SnsApplicationException(
                                POST_NOT_FOUND,
                                String.format("%s not founded", postId)
                        )
                );

        UserEntity userEntity = userEntityRepository.findByUsername(username)
                .orElseThrow(
                        () -> new SnsApplicationException(
                                USER_NOT_FOUND,
                                String.format("%s not founded", username)
                        )
                );

        // check liked
        likeEntityRepository.findByUserAndPost(userEntity, postEntity)
                .ifPresent(it -> {
                    throw new SnsApplicationException(
                            ALREADY_LIKED,
                            String.format("username %s already like post %d", username, postId)
                    );
                });

        // save like
        likeEntityRepository.save(LikeEntity.of(userEntity, postEntity));
    }

    public int likeCount(Integer postId) {
        PostEntity postEntity = postEntityRepository.findById(postId)
                .orElseThrow(
                        () -> new SnsApplicationException(
                                POST_NOT_FOUND,
                                String.format("%s not founded", postId)
                        )
                );

        // count like
        return likeEntityRepository.countByPost(postEntity);
    }

    @Transactional
    public void comment(Integer postId, String username) {

    }
}
