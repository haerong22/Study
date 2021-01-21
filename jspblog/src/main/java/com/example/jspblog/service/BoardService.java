package com.example.jspblog.service;

import com.example.jspblog.domain.board.BoardDao;
import com.example.jspblog.domain.board.dto.WriteReqDto;

public class BoardService {

    private final BoardDao boardDao;

    public BoardService() {
        boardDao = new BoardDao();
    }

    public int 글쓰기(WriteReqDto dto) {
        return boardDao.write(dto);
    }
}
