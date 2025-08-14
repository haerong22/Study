package com.chat.persistence.repository

import com.chat.domain.model.ChatRoom
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ChatRoomRepository : JpaRepository<ChatRoom, Long> {

    @Query("""
        SELECT DISTINCT cr FROM ChatRoom cr 
        JOIN ChatRoomMember crm ON cr.id = crm.chatRoom.id 
        WHERE crm.user.id = :userId AND crm.isActive = true AND cr.isActive = true
        ORDER BY cr.updatedAt DESC
    """)
    fun findUserChatRooms(userId: Long, pageable: Pageable): Page<ChatRoom>

    // 모든 활성 채팅방 조회 (최신순)
    fun findByIsActiveTrueOrderByCreatedAtDesc(): List<ChatRoom>

    // 이름으로 채팅방 검색 (대소문자 무시, 활성 채팅방만)
    fun findByNameContainingIgnoreCaseAndIsActiveTrueOrderByCreatedAtDesc(name: String): List<ChatRoom>
}