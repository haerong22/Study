package com.example.webflux.websocket.repository

import com.example.webflux.websocket.entity.ChatDocument
import org.bson.types.ObjectId
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface ChatMongoCoroutineRepository: CoroutineCrudRepository<ChatDocument, ObjectId> {
}