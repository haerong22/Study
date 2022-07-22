package com.example.simpleblog.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;
import lombok.ToString;

@Setter
@ToString
@AllArgsConstructor
public class PostCreate {

    private String title;
    private String content;
}
