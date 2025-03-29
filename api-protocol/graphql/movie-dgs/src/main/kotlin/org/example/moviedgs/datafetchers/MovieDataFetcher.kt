package org.example.moviedgs.datafetchers

import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsQuery
import com.netflix.graphql.dgs.InputArgument
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
}