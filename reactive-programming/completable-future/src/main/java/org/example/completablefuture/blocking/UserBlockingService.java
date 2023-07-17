package org.example.completablefuture.blocking;

import lombok.RequiredArgsConstructor;
import org.example.completablefuture.blocking.repository.ArticleRepository;
import org.example.completablefuture.blocking.repository.FollowRepository;
import org.example.completablefuture.blocking.repository.ImageRepository;
import org.example.completablefuture.blocking.repository.UserRepository;
import org.example.completablefuture.common.Article;
import org.example.completablefuture.common.Image;
import org.example.completablefuture.common.User;

import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class UserBlockingService {
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    private final ImageRepository imageRepository;
    private final FollowRepository followRepository;

    public Optional<User> getUserById(String id) {
        return userRepository.findById(id)
                .map(user -> {
                    var image = imageRepository.findById(user.getProfileImageId())
                            .map(imageEntity -> {
                                return new Image(imageEntity.getId(), imageEntity.getName(), imageEntity.getUrl());
                            });

                    var articles = articleRepository.findAllByUserId(user.getId())
                            .stream().map(articleEntity ->
                                    new Article(articleEntity.getId(), articleEntity.getTitle(), articleEntity.getContent()))
                            .collect(Collectors.toList());

                    var followCount = followRepository.countByUserId(user.getId());

                    return new User(
                            user.getId(),
                            user.getName(),
                            user.getAge(),
                            image,
                            articles,
                            followCount
                    );
                });
    }
}