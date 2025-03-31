package org.example.moviedgs.datafetchers

import com.example.moviedgs.types.AddReviewInput
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsMutation
import com.netflix.graphql.dgs.DgsSubscription
import com.netflix.graphql.dgs.InputArgument
import org.example.moviedgs.entities.Review
import org.example.moviedgs.repositories.ReviewRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Sinks
import reactor.util.concurrent.Queues

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
}