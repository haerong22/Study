package com.example.jspblog.service;

import com.example.jspblog.domain.board.Board;
import com.example.jspblog.domain.board.BoardDao;
import com.example.jspblog.domain.board.dto.DetailResDto;
import com.example.jspblog.domain.board.dto.UpdateReqDto;
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
    public int 글개수(String keword) {
        return boardDao.count(keword);
    }

    public DetailResDto 글상세보기(int id) {
        int result = boardDao.updateReadCount(id);
        return result == 1 ? boardDao.findById(id) : null;
    }

    public int 글삭제(int id) {
        return boardDao.deleteById(id);
    }

    public int 글수정(UpdateReqDto dto) {
        return boardDao.update(dto);
    }

    public List<Board> 글검색(String keyword, int page) {
        return boardDao.findByKeyword(keyword, page);
    }
}
