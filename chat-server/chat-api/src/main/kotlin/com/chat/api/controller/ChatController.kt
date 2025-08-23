package com.chat.api.controller

import com.chat.domain.dto.ChatRoomDto
import com.chat.domain.dto.ChatRoomMemberDto
import com.chat.domain.dto.CreateChatRoomRequest
import com.chat.domain.dto.MessageDirection
import com.chat.domain.dto.MessageDto
import com.chat.domain.dto.MessagePageRequest
import com.chat.domain.dto.MessagePageResponse
import com.chat.domain.service.ChatService
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/chat-rooms")
class ChatController(
    private val chatService: ChatService
) {

    @PostMapping
    fun createChatRoom(
        @RequestParam createdBy: Long,
        @Valid @RequestBody request: CreateChatRoomRequest
    ): ResponseEntity<ChatRoomDto> {
        val chatRoom = chatService.createChatRoom(request, createdBy)
        return ResponseEntity.ok(chatRoom)
    }

    @GetMapping("/{id}")
    fun getChatRoom(@PathVariable id: Long): ResponseEntity<ChatRoomDto> {
        val chatRoom = chatService.getChatRoom(id)
        return ResponseEntity.ok(chatRoom)
    }

    @GetMapping
    fun getChatRooms(
        @RequestParam userId: Long,
        @PageableDefault(size = 20) pageable: Pageable
    ): ResponseEntity<Page<ChatRoomDto>> {
        val chatRooms = chatService.getChatRooms(userId, pageable)
        return ResponseEntity.ok(chatRooms)
    }

    @PostMapping("/{id}/members")
    fun joinChatRoom(
        @PathVariable id: Long,
        @RequestBody request: Map<String, Long>
    ): ResponseEntity<Void> {
        val userId = request["userId"] ?: throw IllegalArgumentException("userId is required")
        chatService.joinChatRoom(id, userId)
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/{id}/members/me")
    fun leaveChatRoom(
        @PathVariable id: Long,
        @RequestParam userId: Long
    ): ResponseEntity<Void> {
        chatService.leaveChatRoom(id, userId)
        return ResponseEntity.ok().build()
    }

    @GetMapping("/{id}/members")
    fun getChatRoomMembers(@PathVariable id: Long): ResponseEntity<List<ChatRoomMemberDto>> {
        val members = chatService.getChatRoomMembers(id)
        return ResponseEntity.ok(members)
    }

    @GetMapping("/{id}/messages")
    fun getMessages(
        @PathVariable id: Long,
        @RequestParam userId: Long,
        @PageableDefault(size = 50) pageable: Pageable
    ): ResponseEntity<Page<MessageDto>> {
        val messages = chatService.getMessages(id, userId, pageable)
        return ResponseEntity.ok(messages)
    }

    @GetMapping("/{id}/messages/cursor")
    fun getMessagesByCursor(
        @PathVariable id: Long,
        @RequestParam userId: Long,
        @RequestParam(required = false) cursor: Long?,
        @RequestParam(defaultValue = "50") limit: Int,
        @RequestParam(defaultValue = "BEFORE") direction: MessageDirection
    ): ResponseEntity<MessagePageResponse> {
        val request = MessagePageRequest(
            chatRoomId = id,
            cursor = cursor,
            limit = limit.coerceAtMost(100), // 최대 100개로 제한
            direction = direction
        )
        val response = chatService.getMessagesByCursor(request, userId)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/search")
    fun searchChatRooms(
        @RequestParam(required = false, defaultValue = "") q: String,
        @RequestParam userId: Long
    ): ResponseEntity<List<ChatRoomDto>> {
        val chatRooms = chatService.searchChatRooms(q, userId)
        return ResponseEntity.ok(chatRooms)
    }

}