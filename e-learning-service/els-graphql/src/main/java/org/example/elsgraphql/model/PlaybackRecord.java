package org.example.elsgraphql.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.elsplayback.domain.service.PlaybackServiceOuterClass;

import java.io.Serializable;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaybackRecord implements Serializable {
    private Long recordId;
    private Long userId;
    private Long fileId;
    private String startTime;
    private String endTime;

    public static PlaybackRecord fromProto(PlaybackServiceOuterClass.PlaybackRecord proto) {
        PlaybackRecord record = new PlaybackRecord();
        record.setRecordId(proto.getRecordId());
        record.setUserId(proto.getUserId());
        record.setFileId(proto.getFileId());
        record.setStartTime(Instant.ofEpochMilli(proto.getStartTime()).toString()); // Convert to ISO 8601
        record.setEndTime(Instant.ofEpochMilli(proto.getEndTime()).toString());
        return record;
    }

    public static PlaybackServiceOuterClass.PlaybackRecord toProto(PlaybackRecord domain) {
        return PlaybackServiceOuterClass.PlaybackRecord.newBuilder()
                .setRecordId(domain.getRecordId())
                .setUserId(domain.getUserId())
                .setFileId(domain.getFileId())
                .setStartTime(Instant.parse(domain.getStartTime()).toEpochMilli())
                .setEndTime(Instant.parse(domain.getEndTime()).toEpochMilli())
                .build();
    }

}