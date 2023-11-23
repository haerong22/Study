package com.example.rental.application.port.in.command;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClearOverdueItemCommand {
    private String UserId;
    private String UserNm;
    private Integer point;
}
