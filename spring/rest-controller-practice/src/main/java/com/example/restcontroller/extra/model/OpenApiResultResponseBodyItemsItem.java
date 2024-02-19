package com.example.restcontroller.extra.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OpenApiResultResponseBodyItemsItem {
    private String dutyAddr;
    private String dutyName;
    private String dutyTel1;
    private Integer dutyTime1c;
    private String dutyTime1s;
    private Integer dutyTime2c;
    private String dutyTime2s;
    private Integer dutyTime3c;
    private String dutyTime3s;
    private Integer dutyTime4c;
    private String dutyTime4s;
    private Integer dutyTime5c;
    private String dutyTime5s;
    private Integer dutyTime6c;
    private String dutyTime6s;
    private Integer dutyTime7c;
    private String dutyTime7s;
    private String hpid;
    private Integer postCdn1;
    private String postCdn2;
    private Integer rnum;
    private Double wgs84Lat;
    private Double wgs84Lon;
    private String dutyMapimg;
    private String dutyEtc;
    private Integer dutyTime8c;
    private String dutyTime8s;
}
