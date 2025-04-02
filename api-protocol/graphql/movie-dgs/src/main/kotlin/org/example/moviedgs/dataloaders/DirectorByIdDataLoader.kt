package org.example.moviedgs.dataloaders

import com.netflix.graphql.dgs.DgsDataLoader
import org.dataloader.BatchLoader
import org.example.moviedgs.entities.Director
import org.example.moviedgs.repositories.DirectorRepository
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage

@DgsDataLoader(name = "directorById")
class DirectorByIdDataLoader(
    private val directorRepository: DirectorRepository,
): BatchLoader<Long, Director> {

    override fun load(keys: MutableList<Long>): CompletionStage<MutableList<Director>> {
        return CompletableFuture.supplyAsync {
            directorRepository.findAllById(keys)
        }
    }
}