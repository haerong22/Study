package org.example.elsplayback.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.elsplayback.domain.service.PlaybackServiceOuterClass;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
@Setter
@Entity
@Table(name = "event_logs")
public class EventLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "record_id", nullable = false)
    private PlaybackRecord playbackRecord;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "event_type", nullable = false)
    private String eventType;

    @Column(name = "timestamp", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime timestamp;

    public PlaybackServiceOuterClass.EventLog toProto() {
        PlaybackServiceOuterClass.EventLog.Builder builder = PlaybackServiceOuterClass.EventLog.newBuilder();

        if (this.id != null) {
            builder.setEventId(this.id);
        }
        if (this.playbackRecord != null && this.playbackRecord.getId() != null) {
            builder.setRecordId(this.playbackRecord.getId());
        }
        if (this.userId != null) {
            builder.setUserId(this.userId);
        }
        if (this.eventType != null) {
            builder.setEventType(this.eventType);
        }
        if (this.timestamp != null) {
            builder.setTimestamp(this.timestamp.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        }

        return builder.build();
    }

}