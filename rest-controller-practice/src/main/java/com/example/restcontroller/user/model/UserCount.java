package com.example.restcontroller.user.model;

import com.example.restcontroller.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCount {

    private Long totalCount;

    private List<User> data;
}
