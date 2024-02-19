package com.example.restcontroller.notice.model;

import lombok.Data;

import java.util.List;

@Data
public class NoticeDeleteInput {

    List<Long> idList;
}
