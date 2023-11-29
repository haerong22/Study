package com.example.rental.domain.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventResult implements Serializable {

    private EventType eventType;
    private boolean isSuccess;
    private IDName idName;
    private Item item;
    private long point;

}
