package com.example.restcontroller.board.service;

import com.example.restcontroller.board.entity.Board;
import com.example.restcontroller.board.entity.BoardComment;
import com.example.restcontroller.board.entity.BoardReport;
import com.example.restcontroller.board.entity.BoardType;
import com.example.restcontroller.board.model.*;

import java.util.List;

public interface BoardService {

    ServiceResult addBoard(BoardTypeInput boardTypeInput);

    ServiceResult updateBoard(Long id, BoardTypeInput boardTypeInput);

    ServiceResult deleteBoard(Long id);

    List<BoardType> getBoardTypeList();

    ServiceResult setBoardTypeUsing(Long id, BoardTypeUsing boardTypeUsing);

    List<BoardTypeCount> getBoardTypeCount();

    ServiceResult setBoardTop(Long id, boolean topYn);

    ServiceResult setBoardPeriod(Long id, BoardPeriod boardPeriod);

    ServiceResult setBoardHits(Long id, String email);

    ServiceResult setBoardLike(Long id, String email);

    ServiceResult setBoardUnLike(Long id, String email);

    ServiceResult addReport(Long id, String email, BoardReportInput boardReportInput);

    List<BoardReport> boardReportList();

    ServiceResult scrapBoard(Long id, String email);

    ServiceResult deleteScrapBoard(Long id, String email);

    ServiceResult addBookmark(Long id, String email);

    ServiceResult deleteBookmark(Long id, String email);

    List<Board> postList(String email);

    List<BoardComment> commentList(String email);
}
