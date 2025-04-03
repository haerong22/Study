package org.example.moviedgs.dataloaders

import com.netflix.graphql.dgs.DgsDataLoader
import org.dataloader.MappedBatchLoader
import org.example.moviedgs.entities.User
import org.example.moviedgs.repositories.UserRepository
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage

@DgsDataLoader(name = "userById")
class UserByIdDataLoader(
    private val userRepository: UserRepository,
): MappedBatchLoader<Long, User> {

    override fun load(keys: MutableSet<Long>): CompletionStage<Map<Long, User>> {
        return CompletableFuture.supplyAsync {
            userRepository.findAllById(keys)
                .associateBy { it.id!! }
        }
    }
}