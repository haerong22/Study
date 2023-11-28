package com.example.rental.domain.event;

import com.example.rental.domain.model.vo.IDName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class OverdueCleared implements Serializable {

    private IDName idName;
    private long point;
}
