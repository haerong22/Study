package com.example.jspblog.service;

import com.example.jspblog.domain.reply.ReplyDao;
import com.example.jspblog.domain.reply.dto.SaveReqDto;

public class ReplyService {

    private final ReplyDao replyDao;

    public ReplyService() {
        this.replyDao = new ReplyDao();
    }

    public int 댓글쓰기(SaveReqDto dto) {
        return replyDao.save(dto);
    }
}
