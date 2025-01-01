package com.example.webflux.websocket.repository;

import com.example.webflux.websocket.entity.ChatDocument;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ChatMongoRepository extends ReactiveMongoRepository<ChatDocument, ObjectId> {
}
