package com.example.restfulwebservice.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue
    private Integer id;
    private String description;

    // User : Post -> 1 : (0~N)
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;
}
