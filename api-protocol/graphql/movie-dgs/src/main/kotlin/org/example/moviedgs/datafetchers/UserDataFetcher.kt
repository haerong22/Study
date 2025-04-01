package org.example.moviedgs.datafetchers

import com.example.moviedgs.DgsConstants
import com.example.moviedgs.types.AddUserInput
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsData
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment
import com.netflix.graphql.dgs.DgsMutation
import com.netflix.graphql.dgs.DgsQuery
import com.netflix.graphql.dgs.InputArgument
import org.example.moviedgs.entities.Review
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

    @DgsMutation
    fun addUser(
        @InputArgument input: AddUserInput,
    ): User {
        val user = User(
            username = input.username,
            email = input.email,
        )

        return userRepository.save(user)
    }

    @DgsData(
        parentType = DgsConstants.REVIEW.TYPE_NAME,
        field = DgsConstants.REVIEW.User,
    )
    fun getUserByReview(
        dfe: DgsDataFetchingEnvironment
    ): User {
        val review = dfe.getSourceOrThrow<Review>()

        return userRepository.findById(review.user.id!!).get()
    }
}