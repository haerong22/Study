package org.example.moviedgs.datafetchers

import com.example.moviedgs.DgsConstants
import com.example.moviedgs.types.AddReviewInput
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsData
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment
import com.netflix.graphql.dgs.DgsMutation
import com.netflix.graphql.dgs.DgsSubscription
import com.netflix.graphql.dgs.InputArgument
import jakarta.annotation.PreDestroy
import org.example.moviedgs.dataloaders.ReviewsByMovieIdDataLoader
import org.example.moviedgs.dataloaders.ReviewsByUserDataLoader
import org.example.moviedgs.entities.Movie
import org.example.moviedgs.entities.Review
import org.example.moviedgs.entities.User
import org.example.moviedgs.repositories.ReviewRepository
import org.springframework.util.StringUtils
import reactor.core.publisher.Flux
import reactor.core.publisher.Sinks
import reactor.util.concurrent.Queues
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption
import java.util.UUID
import java.util.concurrent.CompletableFuture

@DgsComponent
class ReviewDataFetcher(
    private val userDataFetcher: UserDataFetcher,
    private val movieDataFetcher: MovieDataFetcher,
    private val reviewRepository: ReviewRepository,
) {
    val tempDir: Path = Files.createTempDirectory("review_images")

    @PreDestroy
    fun cleanUp() {
        Files.walk(tempDir)
            .map { it.toFile() }
            .forEach { it.delete() }
    }

    @DgsMutation
    fun addReview(
        @InputArgument input: AddReviewInput,
    ): Review {
        val user = userDataFetcher.user(input.movieId)
        val movie = movieDataFetcher.movie(input.movieId)

        val imageFileUrl = input.imageFile
            ?.let { imageFile ->
                // 파일명 생성
                val fileName = UUID.randomUUID().toString() + "_" + StringUtils.cleanPath(imageFile.originalFilename!!)
                val targetLocation = tempDir.resolve(fileName)

                // 파일 저장
                Files.copy(imageFile.inputStream, targetLocation, StandardCopyOption.REPLACE_EXISTING)

                targetLocation.toString()
            }

        val review = Review(
            rating = input.rating,
            comment = input.comment,
            user = user,
            movie = movie,
            imageFileUrl = imageFileUrl
        )
        reviewRepository.save(review)

        reviewSink.tryEmitNext(review)
        return review
    }

    private val reviewSink = Sinks
        .many()
        .multicast()
        .onBackpressureBuffer<Review>(Queues.SMALL_BUFFER_SIZE, false)

    @DgsSubscription
    fun newReview(
        @InputArgument movieId: Long,
    ): Flux<Review> {
        return reviewSink.asFlux()
            .filter { it.movie.id == movieId }
    }

    @DgsData(
        parentType = DgsConstants.MOVIE.TYPE_NAME,
        field = DgsConstants.MOVIE.Reviews
    )
    fun getReviewsByMovie(
        dfe: DgsDataFetchingEnvironment
    ): CompletableFuture<List<Review>> {
        val movie = dfe.getSourceOrThrow<Movie>()

        val dataLoader = dfe.getDataLoader<Long, List<Review>>(ReviewsByMovieIdDataLoader::class.java)

        return dataLoader.load(movie.id)
    }

    @DgsData(
        parentType = DgsConstants.USER.TYPE_NAME,
        field = DgsConstants.USER.Reviews,
    )
    fun getReviewsByUser(
        dfe: DgsDataFetchingEnvironment
    ): CompletableFuture<List<Review>>? {
        val user = dfe.getSourceOrThrow<User>()

        val dataLoader = dfe.getDataLoader<Long, List<Review>>(ReviewsByUserDataLoader::class.java)

        return dataLoader.load(user.id)
    }
}