package com.example.webflux.websocket.service

import com.example.webflux.websocket.entity.ChatDocument
import com.example.webflux.websocket.helper.logger
import com.example.webflux.websocket.repository.ChatMongoCoroutineRepository
import com.example.webflux.websocket.repository.ChatMongoRepository
import com.mongodb.client.model.changestream.OperationType
import jakarta.annotation.PostConstruct
import org.slf4j.LoggerFactory
import org.springframework.data.mongodb.core.ChangeStreamEvent
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Sinks
import reactor.core.publisher.Sinks.Many
import java.util.concurrent.ConcurrentHashMap

@Service
class ChatCoroutineService(
    private val chatMongoRepository: ChatMongoCoroutineRepository,
    private val reactiveMongoTemplate: ReactiveMongoTemplate,
) {
    companion object {
        val chatSinkMap: MutableMap<String, Many<Chat>> = ConcurrentHashMap()
    }

    private val log = logger<ChatCoroutineService>()

    @PostConstruct
    fun setup() {
        reactiveMongoTemplate.changeStream(ChatDocument::class.java)
            .listen()
            .doOnNext { item: ChangeStreamEvent<ChatDocument> ->
                val target = item.body
                val operationType = item.operationType

                log.info("target: {}", target)
                log.info("type: {}", operationType)
                if (target != null && operationType == OperationType.INSERT) {
                    val from = target.from
                    val to = target.to
                    val message = target.message
                    doSend(from, to, message)
                }
            }
            .subscribe()
    }

    fun register(iam: String): Flux<Chat> {
        val sink = Sinks.many().unicast().onBackpressureBuffer<Chat>()
        chatSinkMap[iam] = sink
        return sink.asFlux()
    }

    suspend fun sendChat(from: String, to: String, message: String) {
        val chatDocument = ChatDocument(from, to, message)
        chatMongoRepository.save(chatDocument)
    }

    private fun doSend(from: String, to: String, message: String) {
        val sink = chatSinkMap[to]

        if (sink == null) {
            val my = chatSinkMap[from]!!
            my.tryEmitNext(Chat("대화 상대가 없습니다.", "system"))
            return
        }

        sink.tryEmitNext(Chat(message, from))
    }
}