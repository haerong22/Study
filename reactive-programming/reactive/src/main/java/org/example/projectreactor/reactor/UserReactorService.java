package org.example.projectreactor.reactor;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.example.projectreactor.common.Article;
import org.example.projectreactor.common.EmptyImage;
import org.example.projectreactor.common.Image;
import org.example.projectreactor.common.User;
import org.example.projectreactor.common.repository.UserEntity;
import org.example.projectreactor.reactor.repository.ArticleReactorRepository;
import org.example.projectreactor.reactor.repository.FollowReactorRepository;
import org.example.projectreactor.reactor.repository.ImageReactorRepository;
import org.example.projectreactor.reactor.repository.UserReactorRepository;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class UserReactorService {
    private final UserReactorRepository userReactorRepository;
    private final ArticleReactorRepository articleReactorRepository;
    private final ImageReactorRepository imageReactorRepository;
    private final FollowReactorRepository followReactorRepository;

    @SneakyThrows
    public Mono<User> getUserById(String id) {
        return userReactorRepository.findById(id)
                .flatMap(this::getUser);
    }

    @SneakyThrows
    private Mono<User> getUser(UserEntity userEntity) {
        Context context = Context.of("user", userEntity);

        Mono<Image> imageMono = imageReactorRepository.findWithContext()
                .map(imageEntity ->
                        new Image(imageEntity.getId(), imageEntity.getName(), imageEntity.getUrl())
                )
                .onErrorReturn(new EmptyImage())
                .doOnSubscribe(subscription -> log.info("subscribe imageRepository"))
                .contextWrite(context);


        Mono<List<Article>> articleMono = articleReactorRepository.findAllWithContext()
                .skip(5)
                .take(2)
                .map(articleEntity ->
                        new Article(articleEntity.getId(), articleEntity.getTitle(), articleEntity.getContent())
                )
                .collectList()
                .doOnSubscribe(subscription -> log.info("subscribe articleRepository"))
                .contextWrite(context);


        Mono<Long> followCountMono = followReactorRepository.countWithContext()
                .doOnSubscribe(subscription -> log.info("subscribe followRepository"))
                .contextWrite(context);

        return Mono.zip(imageMono, articleMono, followCountMono)
                .map(resultTuple -> {
                    Image image = resultTuple.getT1();
                    List<Article> articles = resultTuple.getT2();
                    Long followCount = resultTuple.getT3();

                    Optional<Image> imageOptional = Optional.empty();
                    if (!(image instanceof EmptyImage)) {
                        imageOptional = Optional.of(image);
                    }
                    return new User(
                            userEntity.getId(),
                            userEntity.getName(),
                            userEntity.getAge(),
                            imageOptional,
                            articles,
                            followCount
                    );
                });
    }
}