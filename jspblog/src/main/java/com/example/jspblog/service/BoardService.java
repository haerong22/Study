package com.example.jspblog.service;

import com.example.jspblog.domain.board.Board;
import com.example.jspblog.domain.board.BoardDao;
import com.example.jspblog.domain.board.dto.WriteReqDto;

import java.util.List;

public class BoardService {

    private final BoardDao boardDao;

    public BoardService() {
        boardDao = new BoardDao();
    }

    public int 글쓰기(WriteReqDto dto) {
        return boardDao.write(dto);
    }

    public List<Board> 글목록보기() {
        return boardDao.findAll();
    }
}
