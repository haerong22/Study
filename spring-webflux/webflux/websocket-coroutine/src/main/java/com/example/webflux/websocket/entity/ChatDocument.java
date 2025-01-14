package com.example.webflux.websocket.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "chat")
public class ChatDocument {

    @Id
    private final ObjectId id;
    private final String from;
    private final String to;
    private final String message;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public ChatDocument(String from, String to, String message) {
        this(null, from, to, message, null, null);
    }

    @PersistenceCreator
    public ChatDocument(ObjectId id, String from, String to, String message, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.message = message;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public ChatDocument withId(ObjectId id) {
        return new ChatDocument(id, this.from, this.to, this.message, this.createdAt, this.updatedAt);
    }

    public ObjectId getId() {
        return this.id;
    }

    public String getFrom() {
        return this.from;
    }

    public String getTo() {
        return this.to;
    }

    public String getMessage() {
        return this.message;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof ChatDocument)) return false;
        final ChatDocument other = (ChatDocument) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$from = this.getFrom();
        final Object other$from = other.getFrom();
        if (this$from == null ? other$from != null : !this$from.equals(other$from)) return false;
        final Object this$to = this.getTo();
        final Object other$to = other.getTo();
        if (this$to == null ? other$to != null : !this$to.equals(other$to)) return false;
        final Object this$message = this.getMessage();
        final Object other$message = other.getMessage();
        if (this$message == null ? other$message != null : !this$message.equals(other$message)) return false;
        final Object this$createdAt = this.getCreatedAt();
        final Object other$createdAt = other.getCreatedAt();
        if (this$createdAt == null ? other$createdAt != null : !this$createdAt.equals(other$createdAt)) return false;
        final Object this$updatedAt = this.getUpdatedAt();
        final Object other$updatedAt = other.getUpdatedAt();
        if (this$updatedAt == null ? other$updatedAt != null : !this$updatedAt.equals(other$updatedAt)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ChatDocument;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $from = this.getFrom();
        result = result * PRIME + ($from == null ? 43 : $from.hashCode());
        final Object $to = this.getTo();
        result = result * PRIME + ($to == null ? 43 : $to.hashCode());
        final Object $message = this.getMessage();
        result = result * PRIME + ($message == null ? 43 : $message.hashCode());
        final Object $createdAt = this.getCreatedAt();
        result = result * PRIME + ($createdAt == null ? 43 : $createdAt.hashCode());
        final Object $updatedAt = this.getUpdatedAt();
        result = result * PRIME + ($updatedAt == null ? 43 : $updatedAt.hashCode());
        return result;
    }

    public String toString() {
        return "ChatDocument(id=" + this.getId() + ", from=" + this.getFrom() + ", to=" + this.getTo() + ", message=" + this.getMessage() + ", createdAt=" + this.getCreatedAt() + ", updatedAt=" + this.getUpdatedAt() + ")";
    }
}
