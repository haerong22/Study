package org.example.elsgraphql.service.dummy;

import org.example.elsgraphql.model.EventLog;
import org.example.elsgraphql.model.PlaybackRecord;
import org.example.elsgraphql.service.PlaybackService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class DummyPlaybackService implements PlaybackService {

    private final List<PlaybackRecord> records = new ArrayList<>();
    private final List<EventLog> eventLogs = new ArrayList<>();

    private final AtomicLong recordCounter = new AtomicLong(100);
    private final AtomicLong eventCounter = new AtomicLong(100);

    public DummyPlaybackService() {
        initData();  // Initialize with dummy data
    }

    private void initData() {
        // Adding some dummy playback records
        PlaybackRecord record1 = new PlaybackRecord(recordCounter.getAndIncrement(), 1L, 101L, "2024-01-01T10:00:00Z", "2024-01-01T11:00:00Z");
        PlaybackRecord record2 = new PlaybackRecord(recordCounter.getAndIncrement(), 2L, 102L, "2024-01-02T10:00:00Z", "2024-01-02T11:00:00Z");
        records.add(record1);
        records.add(record2);

        // Adding some dummy event logs
        eventLogs.add(new EventLog(eventCounter.getAndIncrement(), record1.getRecordId(), 1L, "play", "2024-01-01T10:00:00Z"));
        eventLogs.add(new EventLog(eventCounter.getAndIncrement(), record1.getRecordId(), 1L, "pause", "2024-01-01T10:30:00Z"));
        eventLogs.add(new EventLog(eventCounter.getAndIncrement(), record2.getRecordId(), 2L, "play", "2024-01-02T10:00:00Z"));
    }

    public List<PlaybackRecord> findAll() {
        return new ArrayList<>(records);
    }

    @Override
    public PlaybackRecord startRecord(Long userId, Long fileId) {
        PlaybackRecord record = new PlaybackRecord();
        record.setRecordId(recordCounter.getAndIncrement());
        record.setUserId(userId);
        record.setFileId(fileId);

        records.add(record);
        return record;
    }

    @Override
    public PlaybackRecord endRecord(Long recordId) {
        PlaybackRecord endRecord = records.stream().filter(record -> record.getRecordId().equals(recordId))
                .findFirst()
                .orElseThrow();
        endRecord.setEndTime(new Date().toString());
        return endRecord;
    }

    public Optional<PlaybackRecord> findById(Long recordId) {
        return records.stream()
                .filter(record -> record.getRecordId().equals(recordId))
                .findFirst();
    }

    public void delete(Long recordId) {
        records.removeIf(record -> record.getRecordId().equals(recordId));
        // Also remove associated event logs
        eventLogs.removeIf(log -> log.getRecordId().equals(recordId));
    }

    public List<EventLog> findEventLogsByRecordId(Long recordId) {
        return eventLogs.stream()
                .filter(log -> log.getRecordId().equals(recordId))
                .collect(Collectors.toList());
    }

    @Override
    public EventLog logEvent(EventLog log) {
        if (log.getEventId() == null) {
            log.setEventId(eventCounter.getAndIncrement());
        }
        eventLogs.add(log);
        return log;
    }
}