package org.example.completablefuture.future;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.example.completablefuture.common.Article;
import org.example.completablefuture.common.Image;
import org.example.completablefuture.common.User;
import org.example.completablefuture.common.repository.UserEntity;
import org.example.completablefuture.future.repository.ArticleFutureRepository;
import org.example.completablefuture.future.repository.FollowFutureRepository;
import org.example.completablefuture.future.repository.ImageFutureRepository;
import org.example.completablefuture.future.repository.UserFutureRepository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class UserFutureService {
    private final UserFutureRepository userFutureRepository;
    private final ArticleFutureRepository articleFutureRepository;
    private final ImageFutureRepository imageFutureRepository;
    private final FollowFutureRepository followFutureRepository;

    @SneakyThrows
    public CompletableFuture<Optional<User>> getUserById(String id) {
        return userFutureRepository.findById(id)
                .thenComposeAsync(this::getUser);
    }

    @SneakyThrows
    private CompletableFuture<Optional<User>> getUser(Optional<UserEntity> userEntityOptional) {
        if (userEntityOptional.isEmpty()) {
            return CompletableFuture.completedFuture(Optional.empty());
        }

        UserEntity userEntity = userEntityOptional.get();

        CompletableFuture<Optional<Image>> imageFuture = imageFutureRepository.findById(userEntity.getProfileImageId())
                .thenApplyAsync(imageEntityOptional ->
                        imageEntityOptional.map(imageEntity ->
                                new Image(imageEntity.getId(), imageEntity.getName(), imageEntity.getUrl())
                        )
                );


        CompletableFuture<List<Article>> articlesFuture = articleFutureRepository.findAllByUserId(userEntity.getId())
                .thenApplyAsync(articleEntities ->
                        articleEntities.stream()
                                .map(articleEntity ->
                                        new Article(articleEntity.getId(), articleEntity.getTitle(), articleEntity.getContent())
                                )
                                .collect(Collectors.toList())
                );

        CompletableFuture<Long> followCountFuture = followFutureRepository.countByUserId(userEntity.getId());


        return CompletableFuture.allOf(imageFuture, articlesFuture, followCountFuture)
                .thenAcceptAsync(v -> log.info("Three futures are completed"))
                .thenRunAsync(() -> log.info("Three futures are also completed"))
                .thenApplyAsync(v -> {
                    try {
                        Optional<Image> image = imageFuture.get();
                        List<Article> articles = articlesFuture.get();
                        Long followCount = followCountFuture.get();

                        return Optional.of(
                                new User(
                                        userEntity.getId(),
                                        userEntity.getName(),
                                        userEntity.getAge(),
                                        image,
                                        articles,
                                        followCount
                                )
                        );
                    } catch (InterruptedException | ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                });
    }
}