package org.example.moviedgs.dataloaders

import com.netflix.graphql.dgs.DgsDataLoader
import org.dataloader.MappedBatchLoader
import org.example.moviedgs.entities.Review
import org.example.moviedgs.repositories.ReviewRepository
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage

@DgsDataLoader(name = "reviewsById")
class ReviewsByMovieIdDataLoader(
    private val reviewRepository: ReviewRepository,
): MappedBatchLoader<Long, List<Review>> {

    override fun load(keys: MutableSet<Long>): CompletionStage<Map<Long, List<Review>>> {
        return CompletableFuture.supplyAsync {
            reviewRepository.findAllByMovieIdIn(keys)
                .groupBy { it.movie.id!! }
        }
    }
}