package org.example.moviedgs.datafetchers

import org.example.moviedgs.exceptions.CustomNotFoundException
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class MovieIntegrationTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun `movie integration test`() {
        mockMvc.perform(
            post("/graphql")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                        {
                          "query": "query { movie(movieId: 101) { title releaseDate } }"
                        }
                    """.trimIndent()
                )
        )
            .andExpect(
                content().json(
                    """
                    {
                      "data": {
                        "movie": { "title":  "Inception", "releaseDate":  "2010-07-16"}
                      }
                    }
                """.trimIndent()
                )
            )
    }

    @Test
    fun `movie not found`() {
        mockMvc.perform(
            post("/graphql")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                        {
                          "query": "query { movie(movieId: 100) { title releaseDate } }"
                        }
                    """.trimIndent()
                )
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.errors[0].extensions.class").value(CustomNotFoundException::class.java.name))
            .andExpect(jsonPath("$.errors[0].extensions.errorCode").value(1001))
    }

    @Test
    fun `movies integration test`() {
        mockMvc.perform(
            post("/graphql")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """
                        {
                          "query": "query { movies { title releaseDate } }"
                        }
                    """.trimIndent()
                )
        )
            .andExpect(status().isOk)
            .andExpect(
                content().json(
                    """
                        {
                          "data": {
                            "movies": [
                              { "title":  "Inception", "releaseDate":  "2010-07-16"},
                              { "title":  "Interstellar", "releaseDate":  "2014-11-07"},
                              { "title":  "Django Unchained", "releaseDate":  "2012-12-25"}
    ]
                          }
                        }
                    """.trimIndent()
                )
            )
    }
}