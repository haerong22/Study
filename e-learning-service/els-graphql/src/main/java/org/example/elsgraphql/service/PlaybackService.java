package org.example.elsgraphql.service;

import org.example.elsgraphql.model.EventLog;
import org.example.elsgraphql.model.PlaybackRecord;

public interface PlaybackService {
    PlaybackRecord startRecord(Long userId, Long fileId);

    PlaybackRecord endRecord(Long recordId);

    EventLog logEvent(EventLog log);
}
