package com.example.rental.application.port.in.command;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ReturnItemCommand {

    private String userId;
    private String userNm;
    private Long itemId;
    private String itemTitle;
}
