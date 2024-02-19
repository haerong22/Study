package com.example.restcontroller.board.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class BoardPeriod {

    private LocalDate startDate;
    private LocalDate endDate;
}
