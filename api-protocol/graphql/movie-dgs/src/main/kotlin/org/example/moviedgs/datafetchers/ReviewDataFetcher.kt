package org.example.moviedgs.datafetchers

import com.example.moviedgs.DgsConstants
import com.example.moviedgs.types.AddReviewInput
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsData
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment
import com.netflix.graphql.dgs.DgsMutation
import com.netflix.graphql.dgs.DgsSubscription
import com.netflix.graphql.dgs.InputArgument
import org.example.moviedgs.dataloaders.ReviewsByMovieIdDataLoader
import org.example.moviedgs.entities.Movie
import org.example.moviedgs.entities.Review
import org.example.moviedgs.entities.User
import org.example.moviedgs.repositories.ReviewRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Sinks
import reactor.util.concurrent.Queues
import java.util.concurrent.CompletableFuture

@DgsComponent
class ReviewDataFetcher(
    private val userDataFetcher: UserDataFetcher,
    private val movieDataFetcher: MovieDataFetcher,
    private val reviewRepository: ReviewRepository,
) {

    @DgsMutation
    fun addReview(
        @InputArgument input: AddReviewInput,
    ): Review {
        val user = userDataFetcher.user(input.movieId)
        val movie = movieDataFetcher.movie(input.movieId)

        val review = Review(
            rating = input.rating,
            comment = input.comment,
            user = user,
            movie = movie,
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
    ): List<Review> {
        val user = dfe.getSourceOrThrow<User>()

        return reviewRepository.findAllByUser(user)
    }
}