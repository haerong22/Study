package org.example.moviedgs.dataloaders

import com.netflix.graphql.dgs.DgsDataLoader
import org.dataloader.MappedBatchLoader
import org.example.moviedgs.entities.Movie
import org.example.moviedgs.repositories.MovieRepository
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage

@DgsDataLoader(name = "moviesByDirector")
class MoviesByDirectorDataLoader(
    private val movieRepository: MovieRepository,
): MappedBatchLoader<Long, List<Movie>> {

    override fun load(keys: MutableSet<Long>): CompletionStage<Map<Long, List<Movie>>> {
        return CompletableFuture.supplyAsync {
            movieRepository.findAllByDirectorIdIn(keys)
                .groupBy { it.director.id!! }
        }
    }
}