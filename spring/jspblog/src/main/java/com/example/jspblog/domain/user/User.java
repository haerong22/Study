package com.example.jspblog.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    private int id;
    private String username;
    private String password;
    private String email;
    private String address;
    private String userRole;
    private LocalDateTime createDate;
}
