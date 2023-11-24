package com.example.rental.application.port.in.command;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateRentalCardCommand {
    private String userId;
    private String userNm;
}
