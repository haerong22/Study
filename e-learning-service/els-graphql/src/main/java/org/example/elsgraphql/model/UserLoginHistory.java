package org.example.elsgraphql.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginHistory {
    private Long id;
    private String userId;
    private String loginTime;
    private String ipAddress;
}
