package com.example.sns.controller.response;

import com.example.sns.model.Alarm;
import com.example.sns.model.AlarmArgs;
import com.example.sns.model.AlarmType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class AlarmResponse {

    private Integer id;
    private AlarmType alarmType;
    private AlarmArgs args;
    private String text;
    private Timestamp registeredAt;
    private Timestamp updateAt;
    private Timestamp deleteAt;

    public static AlarmResponse fromAlarm(Alarm alarm) {
        return new AlarmResponse(
                alarm.getId(),
                alarm.getAlarmType(),
                alarm.getArgs(),
                alarm.getAlarmType().getAlarmText(),
                alarm.getRegisteredAt(),
                alarm.getUpdatedAt(),
                alarm.getDeletedAt()
        );
    }
}
