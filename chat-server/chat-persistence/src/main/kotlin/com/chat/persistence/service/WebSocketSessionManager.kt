package com.chat.persistence.service

import com.chat.domain.dto.ChatMessage
import com.chat.persistence.redis.RedisMessageBroker
import com.chat.persistence.repository.ChatRoomMemberRepository
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.annotation.PostConstruct
import org.slf4j.LoggerFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import java.util.concurrent.ConcurrentHashMap

@Service
class WebSocketSessionManager(
    private val redisTemplate: RedisTemplate<String, String>,
    private val objectMapper: ObjectMapper,
    private val redisMessageBroker: RedisMessageBroker,
    private val chatRoomMemberRepository: ChatRoomMemberRepository,
) {
    private val log = LoggerFactory.getLogger(javaClass)

    private val userSession = ConcurrentHashMap<Long, MutableSet<WebSocketSession>>()

    private val serverRoomsKeyPrefix = "chat:server:rooms"

    @PostConstruct
    fun initialize() {
        redisMessageBroker.setLocalMessageHandler { roomId, msg ->
            sendMessageToLocalRoom(roomId, msg)
        }
    }

    fun addSession(userId: Long, session: WebSocketSession) {
        log.info("Adding session $userId to server")
        userSession.computeIfAbsent(userId) { mutableSetOf() }.add(session)
    }

    fun removeSession(userId: Long, session: WebSocketSession) {
        userSession[userId]?.remove(session)

        if (userSession[userId]?.isEmpty() == true) {
            userSession.remove(userId)

            val totalConnectedUsers = userSession.values.sumOf { session ->
                session.count { it.isOpen }
            }

            if (totalConnectedUsers == 0) {
                val serverId = redisMessageBroker.getServerId()
                val serverRoomKey = "${serverRoomsKeyPrefix}$serverId"
                val subscribedRooms = redisTemplate.opsForSet().members(serverRoomKey) ?: emptySet()

                subscribedRooms.forEach { roomIdStr ->
                    val roomId = roomIdStr.toLongOrNull()
                    if (roomId != null) {
                        redisMessageBroker.unsubscribeFromRoom(roomId)
                    }
                }

                redisTemplate.delete(serverRoomKey)
                log.info("Removed $totalConnectedUsers $subscribedRooms")
            }
        }
    }

    fun joinRoom(userId: Long, roomId: Long) {
        val serverId = redisMessageBroker.getServerId()

        val serverRoomKey = "${serverRoomsKeyPrefix}$serverId"

        val wasAlreadySubscribed = redisTemplate.opsForSet().isMember(serverRoomKey, roomId.toString()) == true
        if (!wasAlreadySubscribed) {
            redisMessageBroker.subscribeToRoom(roomId)
        }

        redisTemplate.opsForSet().add(serverRoomKey, roomId.toString())

        log.info("Joined $roomId for $userId $serverId to server $serverRoomKey")
    }

    fun sendMessageToLocalRoom(roomId: Long, message: ChatMessage, excludeUserId: Long? = null) {
        val json = objectMapper.writeValueAsString(message)

        userSession.forEach { (userId, session) ->
            if (userId != excludeUserId) {
                val isMember = chatRoomMemberRepository.existsByChatRoomIdAndUserIdAndIsActiveTrue(roomId, userId)

                if (isMember) {
                    val closedSessions = mutableSetOf<WebSocketSession>()

                    session.forEach { s ->
                        if (s.isOpen) {
                            try {
                                s.sendMessage(TextMessage(json))
                                log.info("Sending message to local room $roomId")
                            } catch (e: Exception) {
                                log.error(e.message, e)
                                closedSessions.add(s)
                            }
                        } else {
                            closedSessions.add(s)
                        }
                    }

                    if (closedSessions.isNotEmpty()) {
                        session.removeAll(closedSessions)
                    }
                } else {
                    log.debug("not member of $roomId for $userId")
                }
            }
        }
    }

    fun isUserOnlineLocally(userId: Long): Boolean {
        val sessions = userSession[userId] ?: return false

        val openSessions = sessions.filter { it.isOpen }

        if (openSessions.size != sessions.size) {
            val closedSessions = sessions.filter { !it.isOpen }
            sessions.removeAll(closedSessions.toSet())

            if (sessions.isEmpty()) {
                userSession.remove(userId)
            }
        }

        return openSessions.isNotEmpty()
    }
}