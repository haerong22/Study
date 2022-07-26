package com.example.simpleblog.request;

import lombok.Builder;
import lombok.Data;

import static java.lang.Math.*;

@Data
@Builder
public class PostSearch {

    private final int MAX_SIZE = 2000;

    @Builder.Default
    private Integer page = 1;

    @Builder.Default
    private Integer size = 10;

    public long getOffset() {
        return (long) (max(1, page) - 1) * min(size, MAX_SIZE);
    }

}
