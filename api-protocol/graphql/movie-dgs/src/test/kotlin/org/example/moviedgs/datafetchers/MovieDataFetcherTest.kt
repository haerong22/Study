package org.example.moviedgs.datafetchers

import com.netflix.graphql.dgs.DgsQueryExecutor
import com.netflix.graphql.dgs.autoconfig.DgsExtendedScalarsAutoConfiguration
import com.netflix.graphql.dgs.test.EnableDgsTest
import org.assertj.core.api.Assertions.assertThat
import org.example.moviedgs.entities.Director
import org.example.moviedgs.entities.Movie
import org.example.moviedgs.exceptions.CustomDataFetcherExceptionHandler
import org.example.moviedgs.repositories.MovieRepository
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.bean.override.mockito.MockitoBean
import java.time.LocalDate
import java.util.Optional

@EnableDgsTest
@SpringBootTest(
    classes = [
        MovieDataFetcher::class,
        DgsExtendedScalarsAutoConfiguration::class,
        CustomDataFetcherExceptionHandler::class,
    ]
)
class MovieDataFetcherTest {

    @Autowired
    lateinit var dgsQueryExecutor: DgsQueryExecutor

    @MockitoBean
    lateinit var movieRepository: MovieRepository

    @Test
    fun movies() {
        // given
        val director = Director(id = 1, name = "director")
        Mockito.`when`(movieRepository.findAll()).thenAnswer {
            listOf(
                Movie(id = 1, title = "movie1", director = director, releaseDate = LocalDate.now()),
                Movie(id = 2, title = "movie2", director = director, releaseDate = LocalDate.now()),
                Movie(id = 3, title = "movie3", director = director, releaseDate = LocalDate.now()),
            )
        }

        // when
        val titles: List<String> = dgsQueryExecutor.executeAndExtractJsonPath(
            """
                {
                    movies {
                        title
                    }
                }
            """.trimIndent(),
            "data.movies[*].title",
        )

        // then
        assertThat(titles).contains("movie1")
        assertThat(titles.size).isEqualTo(3)
    }

    @Test
    fun movie() {
        // given
        val director = Director(id = 1, name = "director")
        Mockito.`when`(movieRepository.findById(1)).thenAnswer {
            Optional.of(Movie(id = 1, title = "movie1", director = director, releaseDate = LocalDate.now()))
        }

        // when
        val title: String = dgsQueryExecutor.executeAndExtractJsonPath(
            """
                {
                    movie(movieId: 1) {
                        title
                    }
                }
            """.trimIndent(),
            "data.movie.title",
        )

        // then
        assertThat(title).isEqualTo("movie1")
    }

    @Test
    fun movieWithException() {
        // given
        Mockito.`when`(movieRepository.findById(1)).thenAnswer { Optional.ofNullable(null) }

        // when
        val result = dgsQueryExecutor.execute(
            """
                {
                    movie(movieId: 1) {
                        title
                    }
                }
            """.trimIndent()
        )

        // then
        assertThat(result.errors).isNotEmpty
        assertThat(result.errors[0].message).contains("Movie Not Found.")
        assertThat(result.errors[0].extensions["errorCode"]).isEqualTo(1001)
    }
}