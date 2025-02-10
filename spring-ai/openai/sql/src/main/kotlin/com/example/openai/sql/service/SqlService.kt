package com.example.openai.sql.service

import org.springframework.ai.chat.client.ChatClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service
import java.nio.charset.Charset
import java.util.Locale

@Service
class SqlService(
    @Value("classpath:/schema.sql")
    private val ddlResource: Resource,
    @Value("classpath:/sql-prompt-template.st")
    private val sqlPromptTemplate: Resource,
    private val jdbcTemplate: JdbcTemplate,
    chatClientBuilder: ChatClient.Builder,
) {
    private val chatClient = chatClientBuilder.build()

    fun queryForList(question: String): SqlResponse {
        val schema = ddlResource.getContentAsString(Charset.defaultCharset())

        val query = chatClient.prompt()
            .user { userSpec ->
                userSpec
                    .text(sqlPromptTemplate)
                    .param("question", question)
                    .param("ddl", schema)
            }
            .call()
            .content()!!

        if (query.lowercase(Locale.getDefault()).startsWith("select")) {
            return SqlResponse(query, jdbcTemplate.queryForList(query))
        }
        return SqlResponse(query, listOf())
    }
}