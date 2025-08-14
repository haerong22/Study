package com.chat.persistence.redis

import com.chat.domain.dto.ChatMessage
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import org.slf4j.LoggerFactory
import org.springframework.data.redis.connection.Message
import org.springframework.data.redis.connection.MessageListener
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.listener.ChannelTopic
import org.springframework.data.redis.listener.RedisMessageListenerContainer
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.concurrent.ConcurrentHashMap

@Service
class RedisMessageBroker(
    private val redisTemplate: RedisTemplate<String, String>,
    private val messageListenerContainer: RedisMessageListenerContainer,
    private val objectMapper: ObjectMapper,
) : MessageListener {
    private val log = LoggerFactory.getLogger(javaClass)
    private val serverId = System.getenv("HOSTNAME") ?: "server-${System.currentTimeMillis()}"
    private val processedMessages = ConcurrentHashMap<String, Long>()
    private val subscribeRooms = ConcurrentHashMap.newKeySet<Long>()
    private var localMessageHandler: ((Long, ChatMessage) -> Unit)? = null

    fun getServerId() = serverId

    @PostConstruct
    fun initialize() {
        log.info("Initializing RedisMessageListenerContainer")

        Thread {
            try {
                Thread.sleep(30000)
                cleanupProcessedMessages()
            } catch (e: Exception) {
                log.error("Error in initializing RedisMessageListenerContainer", e)
            }
        }.apply {
            isDaemon = true
            name = "redis-broker-cleanup"
            start()
        }
    }

    @PreDestroy
    fun cleanup() {
        subscribeRooms.forEach { roomId ->
            unsubscribeFromRoom(roomId)
        }
        log.info("Removing RedisMessageListenerContainer")
    }

    fun setLocalMessageHandler(handler: (Long, ChatMessage) -> Unit) {
        this.localMessageHandler = handler
    }

    fun subscribeToRoom(roomId: Long) {
        if (subscribeRooms.add(roomId)) {
            val topic = ChannelTopic("chat.room.$roomId")
            messageListenerContainer.addMessageListener(this, topic)
            log.info("Subscribe to $roomId")
        } else {
            log.error("Room $roomId does not exist")
        }

    }

    fun unsubscribeFromRoom(roomId: Long) {
        if (subscribeRooms.remove(roomId)) {
            val topic = ChannelTopic("chat.room.$roomId")
            messageListenerContainer.removeMessageListener(this, topic)
            log.info("Unsubscribe from $roomId")
        } else {
            log.error("Room $roomId does not exist")
        }
    }

    fun broadcastToRoom(roomId: Long, message: ChatMessage, excludeServerId: String? = null) {
        try {
            val distributedMessage = DistributedMessage(
                id = "$serverId-${System.currentTimeMillis()}-${System.nanoTime()}",
                serverId = serverId,
                roomId = roomId,
                excludeServerId = excludeServerId,
                timestamp = LocalDateTime.now(),
                payload = message,
            )

            val json = objectMapper.writeValueAsString(distributedMessage)
            redisTemplate.convertAndSend("chat.room.$roomId", json)

            log.info("Broadcast to $roomId to $json")
        } catch (e: Exception) {
            log.error("Error broadcast to $roomId", e)
        }
    }

    override fun onMessage(message: Message, pattern: ByteArray?) {
        try {
            val json = String(message.body)
            val distributedMessage = objectMapper.readValue(json, DistributedMessage::class.java)

            if (distributedMessage.excludeServerId == serverId) {
                log.error("excludeServerId to $serverId")
                return
            }

            if (processedMessages.containsKey(distributedMessage.id)) {
                log.error("proceedMessages $distributedMessage")
                return
            }

            localMessageHandler?.invoke(distributedMessage.roomId, distributedMessage.payload)

            processedMessages[distributedMessage.id] = System.currentTimeMillis()

            if (processedMessages.size > 10000) {
                val oldestEntries = processedMessages.entries.sortedBy { it.value }
                    .take(processedMessages.size - 10000)

                oldestEntries.forEach { processedMessages.remove(it.key) }
            }

            log.info("processedMessages ${distributedMessage.id}")

        } catch (e: Exception) {
            log.error("Error in onMessage", e)
        }
    }

    private fun cleanupProcessedMessages() {
        val now = System.currentTimeMillis()
        val expiredKeys = processedMessages.filter { (_, time) ->
            now - time > 60000
        }.keys

        expiredKeys.forEach { processedMessages.remove(it) }

        if (expiredKeys.isNotEmpty()) {
            log.info("Remove ${processedMessages.size} messages from Redis")
        }
    }

    data class DistributedMessage(
        val id: String,
        val serverId: String,
        val roomId: Long,
        val excludeServerId: String?,
        val timestamp: LocalDateTime,
        val payload: ChatMessage
    )
}