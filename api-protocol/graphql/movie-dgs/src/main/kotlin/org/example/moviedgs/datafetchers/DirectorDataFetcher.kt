package org.example.moviedgs.datafetchers

import com.example.moviedgs.DgsConstants
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsData
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment
import org.example.moviedgs.dataloaders.DirectorByIdDataLoader
import org.example.moviedgs.entities.Director
import org.example.moviedgs.entities.Movie
import org.example.moviedgs.repositories.DirectorRepository
import java.util.concurrent.CompletableFuture

@DgsComponent
class DirectorDataFetcher(
    private val directorRepository: DirectorRepository,
) {

    @DgsData(
        parentType = DgsConstants.MOVIE.TYPE_NAME,
        field = DgsConstants.MOVIE.Director
    )
    fun getDirectorByMovie(
        dfe: DgsDataFetchingEnvironment,
    ): CompletableFuture<Director>? {
        val movie = dfe.getSourceOrThrow<Movie>()

        val dataLoader = dfe.getDataLoader<Long, Director>(DirectorByIdDataLoader::class.java)

        return dataLoader.load(movie.director.id)
    }
}