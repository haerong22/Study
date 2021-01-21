package com.example.jspblog.service;

import com.example.jspblog.domain.board.Board;
import com.example.jspblog.domain.board.BoardDao;
import com.example.jspblog.domain.board.dto.DetailResDto;
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

    public List<Board> 글목록보기(int page) {
        return boardDao.findAll(page);
    }

    public int 글개수() {
        return boardDao.count();
    }

    public DetailResDto 글상세보기(int id) {
        return boardDao.findById(id);
    }
}
