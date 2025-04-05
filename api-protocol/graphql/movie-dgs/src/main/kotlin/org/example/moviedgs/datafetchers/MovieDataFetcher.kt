package org.example.moviedgs.datafetchers

import com.example.moviedgs.DgsConstants
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsData
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment
import com.netflix.graphql.dgs.DgsQuery
import com.netflix.graphql.dgs.InputArgument
import org.example.moviedgs.dataloaders.MoviesByDirectorDataLoader
import org.example.moviedgs.entities.Director
import org.example.moviedgs.entities.Movie
import org.example.moviedgs.exceptions.CustomNotFoundException
import org.example.moviedgs.repositories.MovieRepository
import java.util.concurrent.CompletableFuture

@DgsComponent
class MovieDataFetcher(
    private val movieRepository: MovieRepository,
) {

    @DgsQuery
    fun movies(): MutableList<Movie> {
        return movieRepository.findAll()
    }

    @DgsQuery
    fun movie(
        @InputArgument movieId: Long,
    ): Movie {
        return movieRepository.findById(movieId).orElseThrow { CustomNotFoundException("Movie Not Found.") }
    }

    @DgsData(
        parentType = DgsConstants.DIRECTOR.TYPE_NAME,
        field = DgsConstants.DIRECTOR.Movies,
    )
    fun getMoviesByDirector(
        dfe: DgsDataFetchingEnvironment
    ): CompletableFuture<List<Movie>>? {
        val director = dfe.getSourceOrThrow<Director>()

        val dataLoader = dfe.getDataLoader<Long, List<Movie>>(MoviesByDirectorDataLoader::class.java)

        return dataLoader.load(director.id)
    }
}