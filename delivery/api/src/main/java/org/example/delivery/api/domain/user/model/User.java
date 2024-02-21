package org.example.delivery.api.domain.user.model;

import lombok.Builder;
import lombok.Getter;
import org.example.delivery.db.user.enums.UserStatus;

import java.time.LocalDateTime;

@Getter
@Builder
public class User {

    private Long id;
    private String name;
    private String email;
    private String password;
    private UserStatus status;
    private String address;

    private LocalDateTime registeredAt;
    private LocalDateTime unregisteredAt;
    private LocalDateTime lastLoginAt;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public UserStatus getStatus() {
        return status;
    }

    public String getAddress() {
        return address;
    }

    public LocalDateTime getRegisteredAt() {
        return registeredAt;
    }

    public LocalDateTime getUnregisteredAt() {
        return unregisteredAt;
    }

    public LocalDateTime getLastLoginAt() {
        return lastLoginAt;
    }
}
