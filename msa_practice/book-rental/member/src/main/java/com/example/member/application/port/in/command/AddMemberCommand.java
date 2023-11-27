package com.example.member.application.port.in.command;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AddMemberCommand {

    private String id;
    private String name;
    private String password;
    private String email;
}
