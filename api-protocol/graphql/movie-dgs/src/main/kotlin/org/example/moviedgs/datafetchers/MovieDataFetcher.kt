package org.example.moviedgs.datafetchers

import com.example.moviedgs.DgsConstants
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsData
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment
import com.netflix.graphql.dgs.DgsQuery
import com.netflix.graphql.dgs.InputArgument
import org.example.moviedgs.entities.Director
import org.example.moviedgs.entities.Movie
import org.example.moviedgs.repositories.MovieRepository

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
        return movieRepository.findById(movieId).orElseThrow { RuntimeException("Movie Not Found.") }
    }

    @DgsData(
        parentType = DgsConstants.DIRECTOR.TYPE_NAME,
        field = DgsConstants.DIRECTOR.Movies,
    )
    fun getMoviesByDirector(
        dfe: DgsDataFetchingEnvironment
    ): List<Movie> {
        val director = dfe.getSourceOrThrow<Director>()

        return movieRepository.findAllByDirector(director)
    }
}