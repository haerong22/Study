package com.example.member.domain.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OverdueCleared implements Serializable {

    private IDName idName;
    private long point;
}
