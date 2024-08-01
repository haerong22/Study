CREATE TABLE playback_records
(
    id  INT          NOT NULL AUTO_INCREMENT,
    user_id    VARCHAR(255) NOT NULL,
    file_id    VARCHAR(255) NOT NULL,
    start_time TIMESTAMP    NOT NULL,
    end_time   TIMESTAMP,
    PRIMARY KEY (id)
);

CREATE TABLE event_logs
(
    id   INT         NOT NULL AUTO_INCREMENT,
    record_id  INT         NOT NULL,
    user_id    INT         NOT NULL,
    event_type VARCHAR(50) NOT NULL,
    timestamp  TIMESTAMP   NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (record_id) REFERENCES playback_records (id)
);