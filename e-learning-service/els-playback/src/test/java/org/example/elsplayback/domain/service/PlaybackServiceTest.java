package org.example.elsplayback.domain.service;

import io.grpc.stub.StreamObserver;
import org.example.elsplayback.domain.entity.EventLog;
import org.example.elsplayback.domain.entity.PlaybackRecord;
import org.example.elsplayback.domain.repository.EventLogRepository;
import org.example.elsplayback.domain.repository.PlaybackRecordRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class PlaybackServiceTest {

    @Mock
    private PlaybackRecordRepository playbackRecordRepository;

    @Mock
    private EventLogRepository eventLogRepository;

    @Mock
    private StreamObserver<PlaybackServiceOuterClass.StartRecordResponse> startResponseObserver;

    @Mock
    private StreamObserver<PlaybackServiceOuterClass.EndRecordResponse> endResponseObserver;

    @Mock
    private StreamObserver<PlaybackServiceOuterClass.LogEventResponse> logEventResponseObserver;

    @InjectMocks
    private PlaybackService playbackService;

    @Test
    void start_record() {
        // given
        given(playbackRecordRepository.save(any())).willReturn(new PlaybackRecord());

        PlaybackServiceOuterClass.StartRecordRequest request = PlaybackServiceOuterClass.StartRecordRequest.newBuilder()
                .setUserId(1L)
                .setFileId(1L)
                .build();

        playbackService.startRecord(request, startResponseObserver);

        verify(playbackRecordRepository).save(any(PlaybackRecord.class));
        verify(startResponseObserver).onNext(any());
        verify(startResponseObserver).onCompleted();
    }

    @Test
    void end_record() {
        PlaybackRecord record = new PlaybackRecord();
        record.setId(1L);
        record.setUserId(1L);
        record.setFileId(1L);
        record.setStartTime(LocalDateTime.now());

        given(playbackRecordRepository.findById(anyLong())).willReturn(Optional.of(record));

        PlaybackServiceOuterClass.EndRecordRequest request = PlaybackServiceOuterClass.EndRecordRequest.newBuilder()
                .setRecordId(1L)
                .build();

        playbackService.endRecord(request, endResponseObserver);

        verify(playbackRecordRepository).save(record);
        verify(endResponseObserver).onNext(any());
        verify(endResponseObserver).onCompleted();
    }

    @Test
    void log_event() {
        PlaybackRecord record = new PlaybackRecord();
        record.setId(1L);
        record.setUserId(1L);
        record.setFileId(1L);
        record.setStartTime(LocalDateTime.now());

        when(playbackRecordRepository.findById(anyLong())).thenReturn(Optional.of(record));

        EventLog expectedEvent = new EventLog();
        expectedEvent.setPlaybackRecord(record);
        expectedEvent.setUserId(1L); // Matching the userId type
        expectedEvent.setEventType("PLAY");
        expectedEvent.setTimestamp(LocalDateTime.now());

        // Mock the save method to return the expected event
        given(eventLogRepository.save(any(EventLog.class))).willReturn(expectedEvent);


        PlaybackServiceOuterClass.LogEventRequest request = PlaybackServiceOuterClass.LogEventRequest.newBuilder()
                .setEvent(PlaybackServiceOuterClass.EventLog.newBuilder()
                        .setRecordId(1L)
                        .setUserId(1L)
                        .setEventType("PLAY"))
                .build();

        playbackService.logEvent(request, logEventResponseObserver);

        verify(eventLogRepository).save(any(EventLog.class));
        verify(logEventResponseObserver).onNext(any());
        verify(logEventResponseObserver).onCompleted();
    }
}