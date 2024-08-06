package org.example.elsgraphql.service.impl;

import net.devh.boot.grpc.client.inject.GrpcClient;
import org.example.elsgraphql.model.EventLog;
import org.example.elsgraphql.model.PlaybackRecord;
import org.example.elsgraphql.service.PlaybackService;
import org.example.elsplayback.domain.service.PlaybackServiceGrpc;
import org.example.elsplayback.domain.service.PlaybackServiceOuterClass;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@Service
public class PlaybackServiceImpl implements PlaybackService {

    @GrpcClient("els-playback")
    private PlaybackServiceGrpc.PlaybackServiceBlockingStub playbackServiceStub;

    @Override
    public PlaybackRecord startRecord(Long userId, Long fileId) {
        PlaybackServiceOuterClass.StartRecordRequest request = PlaybackServiceOuterClass.StartRecordRequest.newBuilder()
                .setUserId(userId)
                .setFileId(fileId)
                .build();

        PlaybackServiceOuterClass.StartRecordResponse response = playbackServiceStub.startRecord(request);
        return PlaybackRecord.fromProto(response.getRecord());
    }

    @Override
    public PlaybackRecord endRecord(Long recordId) {
        PlaybackServiceOuterClass.EndRecordRequest request = PlaybackServiceOuterClass.EndRecordRequest.newBuilder()
                .setRecordId(recordId)
                .build();

        PlaybackServiceOuterClass.EndRecordResponse response = playbackServiceStub.endRecord(request);
        return PlaybackRecord.fromProto(response.getRecord());
    }

    @Override
    public EventLog logEvent(EventLog log) {
        PlaybackServiceOuterClass.LogEventRequest request = PlaybackServiceOuterClass.LogEventRequest.newBuilder()
                .setEvent(EventLog.toProto(log))
                .build();

        PlaybackServiceOuterClass.LogEventResponse response = playbackServiceStub.logEvent(request);

        return EventLog.fromProto(response.getEvent());
    }
}
