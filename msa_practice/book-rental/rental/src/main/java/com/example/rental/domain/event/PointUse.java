package com.example.rental.domain.event;

import com.example.rental.domain.model.vo.IDName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PointUse implements Serializable {

    private IDName idName;
    private long point;
}
