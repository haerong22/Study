package com.example.rental.application.port.in.command;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OverdueItemCommand {

    private String userId;
    private String userNm;
    private Long itemId;
    private String itemTitle;
}