package org.example.moviedgs.dataloaders

import com.netflix.graphql.dgs.DgsDataLoader
import org.dataloader.BatchLoader
import org.example.moviedgs.entities.Director
import org.example.moviedgs.repositories.DirectorRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage
import java.util.concurrent.Executor

@DgsDataLoader(name = "directorById", caching = true, batching = true, maxBatchSize = 100)
class DirectorByIdDataLoader(
    private val directorRepository: DirectorRepository,
    @Qualifier("DataLoaderThreadPool")
    private val executor: Executor,
) : BatchLoader<Long, Director> {

    private val log = LoggerFactory.getLogger(this::class.java)

    override fun load(keys: MutableList<Long>): CompletionStage<MutableList<Director>> {
        return CompletableFuture.supplyAsync({
            log.info("director dataLoader")
            directorRepository.findAllById(keys)
        }, executor)
    }
}