package org.example.moviedgs.datafetchers

import com.fasterxml.jackson.databind.ObjectMapper
import com.netflix.graphql.dgs.DgsQueryExecutor
import com.netflix.graphql.dgs.test.EnableDgsTest
import graphql.ExecutionResult
import org.assertj.core.api.Assertions.assertThat
import org.example.moviedgs.entities.Director
import org.example.moviedgs.entities.Movie
import org.example.moviedgs.entities.Review
import org.example.moviedgs.entities.User
import org.example.moviedgs.repositories.ReviewRepository
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.reactivestreams.Publisher
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.bean.override.mockito.MockitoBean
import java.time.LocalDate

@EnableDgsTest
@SpringBootTest
class ReviewSubscriptionTest {

    @MockitoBean
    lateinit var userDataFetcher: UserDataFetcher

    @MockitoBean
    lateinit var movieDataFetcher: MovieDataFetcher

    @Autowired
    lateinit var reviewRepository: ReviewRepository

    @Autowired
    lateinit var dgsQueryExecutor: DgsQueryExecutor

    @Test
    fun newReview() {
        val executeResult = dgsQueryExecutor.execute(
            """
                subscription {
                    newReview(movieId: 101) {
                        id
                    }
                }
            """.trimIndent()
        )

        val reviewPublisher = executeResult.getData<Publisher<ExecutionResult>>()
        val reviews = mutableListOf<Review>()

        addReview()
        addReview()

        reviewPublisher.subscribe(
            object : Subscriber<ExecutionResult> {
                override fun onSubscribe(p0: Subscription) {
                    p0.request(2)
                }

                override fun onNext(p0: ExecutionResult) {
                    val data = p0.getData<Map<String, Any>>()

                    reviews.add(ObjectMapper().convertValue(data["newReview"], Review::class.java))
                }

                override fun onError(p0: Throwable) {
                    println("Error: ${p0.message}")
                }

                override fun onComplete() {
                    println("Complete!")
                }
            }
        )

        assertThat(reviews.size).isEqualTo(2)
    }

    private fun addReview() {
        Mockito.`when`(movieDataFetcher.movie(101L))
            .thenAnswer { Movie(id = 101, title = "movie1", LocalDate.now(), Director(id = 101, "director1")) }
        Mockito.`when`(userDataFetcher.user(101L))
            .thenAnswer { User(id = 101, username = "user1", email = "user1@email.com") }

        dgsQueryExecutor.execute(
            """
                mutation AddReview {
                    addReview(input: {movieId: 101, userId: 101, rating: 5, comment: "comment"}) {
                        id
                    }
                }
            """.trimIndent()
        )
    }
}