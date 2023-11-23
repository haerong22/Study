package com.example.rental.application.port.in.command;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateRentalCardCommand {
    private String userId;
    private String userNm;
}
