package com.example.restcontroller.extra.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OpenApiResultResponseBody {

    private OpenApiResultResponseBodyItems items;
    private Integer numOfRows;
    private Integer pageNo;
    private Integer totalCount;

}
