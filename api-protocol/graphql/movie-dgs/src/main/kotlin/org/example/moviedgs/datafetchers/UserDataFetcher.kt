package org.example.moviedgs.datafetchers

import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsQuery
import com.netflix.graphql.dgs.InputArgument
import org.example.moviedgs.entities.User
import org.example.moviedgs.repositories.UserRepository

@DgsComponent
class UserDataFetcher(
    private val userRepository: UserRepository,
) {

    @DgsQuery
    fun user(
        @InputArgument userId: Long,
    ): User {
        return userRepository.findById(userId).orElseThrow { RuntimeException("User Not Found.") }
    }
}