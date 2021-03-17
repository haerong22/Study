package com.example.restcontroller.board.service;

import com.example.restcontroller.board.entity.*;
import com.example.restcontroller.board.exception.BoardTypeNotFoundException;
import com.example.restcontroller.board.model.*;
import com.example.restcontroller.board.repository.*;
import com.example.restcontroller.common.exception.BizException;
import com.example.restcontroller.user.entity.User;
import com.example.restcontroller.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class BoardServiceImpl implements BoardService {

    private final BoardTypeRepository boardTypeRepository;
    private final BoardRepository boardRepository;
    private final BoardTypeCustomRepository boardTypeCustomRepository;
    private final BoardHitsRepository boardHitsRepository;
    private final BoardLikeRepository boardLikeRepository;
    private final BoardReportRepository boardReportRepository;
    private final BoardScrapRepository boardScrapRepository;
    private final BoardBookmarkRepository boardBookmarkRepository;
    private final BoardCommentRepository boardCommentRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public ServiceResult addBoard(BoardTypeInput boardTypeInput) {
        int result = boardTypeRepository.countByBoardName(boardTypeInput.getName());
        if (result > 0) {
            return ServiceResult.fail("이미 동일한 게시판명이 존재합니다.");
        }

        BoardType boardType = BoardType.builder()
                .boardName(boardTypeInput.getName())
                .regDate(LocalDateTime.now())
                .build();

        boardTypeRepository.save(boardType);

        return ServiceResult.success();
    }

    @Transactional
    @Override
    public ServiceResult updateBoard(Long id, BoardTypeInput boardTypeInput) {
        int result = boardTypeRepository.countByBoardName(boardTypeInput.getName());
        if (result > 0) {
            return ServiceResult.fail("이미 동일한 게시판명이 존재합니다.");
        }

        BoardType boardTypeEntity = boardTypeRepository.findById(id)
                .orElseThrow(() -> new BoardTypeNotFoundException("삭제할 게시판 타입이 없습니다."));

        boardTypeEntity.setBoardName(boardTypeInput.getName());
        boardTypeEntity.setUpdateDate(LocalDateTime.now());

//        boardTypeRepository.save(boardTypeEntity);

        return ServiceResult.success();
    }

    @Transactional
    @Override
    public ServiceResult deleteBoard(Long id) {
        BoardType boardType = boardTypeRepository.findById(id)
                .orElseThrow(() -> new BoardTypeNotFoundException("삭제할 게시판 타입이 없습니다."));

        if (boardRepository.countByBoardType(boardType) > 0 ) {
            return ServiceResult.fail("삭제할 게시판타입의 게시글이 존재합니다.");
        }

        boardTypeRepository.delete(boardType);
        return ServiceResult.success();
    }

    @Override
    public List<BoardType> getBoardTypeList() {
        return boardTypeRepository.findAll();
    }

    @Transactional
    @Override
    public ServiceResult setBoardTypeUsing(Long id, BoardTypeUsing boardTypeUsing) {
        BoardType boardType = boardTypeRepository.findById(id)
                .orElseThrow(() -> new BoardTypeNotFoundException("게시판 타입이 없습니다."));

        boardType.setUsingYn(boardTypeUsing.isUsingYn());

        return ServiceResult.success();
    }

    @Override
    public List<BoardTypeCount> getBoardTypeCount() {
        return boardTypeCustomRepository.getBoardTypeCount();
    }

    @Transactional
    @Override
    public ServiceResult setBoardTop(Long id, boolean topYn) {
        Optional<Board> board = boardRepository.findById(id);
        if (!board.isPresent()) {
            return ServiceResult.fail("게시글이 존재하지 않습니다");
        }

        Board boardEntity = board.get();
        if (boardEntity.isTopYn() == topYn) {
            String msg = topYn ? "이미 게시글이 최상단에 배치되어 있습니다." : "이미 게시글이 최상단 배치가 해제되어 있습니다.";
            return ServiceResult.fail(msg);
        }

        boardEntity.setTopYn(topYn);
        return ServiceResult.success();
    }

    @Transactional
    @Override
    public ServiceResult setBoardPeriod(Long id, BoardPeriod boardPeriod) {
        Optional<Board> board = boardRepository.findById(id);
        if (!board.isPresent()) {
            return ServiceResult.fail("게시글이 존재하지 않습니다");
        }
        Board boardEntity = board.get();
        boardEntity.setPublishStartDate(boardPeriod.getStartDate());
        boardEntity.setPublishEndDate(boardPeriod.getEndDate());
        return ServiceResult.success();
    }

    @Transactional
    @Override
    public ServiceResult setBoardHits(Long id, String email) {
        Optional<Board> board = boardRepository.findById(id);
        if (!board.isPresent()) {
            return ServiceResult.fail("게시글이 존재하지 않습니다");
        }
        Board boardEntity = board.get();

        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (!optionalUser.isPresent()) {
            return ServiceResult.fail("회원 정보가 존재하지 않습니다");
        }
        User userEntity = optionalUser.get();
        if (boardHitsRepository.countByBoardAndUser(boardEntity, userEntity) > 0) {
            return ServiceResult.fail("이미 조회수가 있습니다.");
        }

        boardHitsRepository.save(BoardHits.builder()
                .board(boardEntity)
                .user(userEntity)
                .regDate(LocalDateTime.now())
                .build());

        return ServiceResult.success();
    }

    @Transactional
    @Override
    public ServiceResult setBoardLike(Long id, String email) {
        Optional<Board> board = boardRepository.findById(id);
        if (!board.isPresent()) {
            return ServiceResult.fail("게시글이 존재하지 않습니다");
        }
        Board boardEntity = board.get();

        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (!optionalUser.isPresent()) {
            return ServiceResult.fail("회원 정보가 존재하지 않습니다");
        }

        User userEntity = optionalUser.get();
        if (boardLikeRepository.countByBoardAndUser(boardEntity, userEntity) > 0) {
            return ServiceResult.fail("이미 좋아요를 누른 게시글 입니다.");
        }

        boardLikeRepository.save(BoardLike.builder()
                .board(boardEntity)
                .user(userEntity)
                .regDate(LocalDateTime.now())
                .build());

        return ServiceResult.success();
    }

    @Transactional
    @Override
    public ServiceResult setBoardUnLike(Long id, String email) {
        Optional<Board> board = boardRepository.findById(id);
        if (!board.isPresent()) {
            return ServiceResult.fail("게시글이 존재하지 않습니다");
        }
        Board boardEntity = board.get();

        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (!optionalUser.isPresent()) {
            return ServiceResult.fail("회원 정보가 존재하지 않습니다");
        }

        User userEntity = optionalUser.get();
        if (boardLikeRepository.countByBoardAndUser(boardEntity, userEntity) < 1) {
            return ServiceResult.fail("좋아요한 내용이 없습니다.");
        }
        boardLikeRepository.deleteByBoardAndUser(boardEntity, userEntity);

        return ServiceResult.success();
    }

    @Transactional
    @Override
    public ServiceResult addReport(Long id, String email, BoardReportInput boardReportInput) {
        Optional<Board> board = boardRepository.findById(id);
        if (!board.isPresent()) {
            return ServiceResult.fail("게시글이 존재하지 않습니다");
        }
        Board boardEntity = board.get();

        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (!optionalUser.isPresent()) {
            return ServiceResult.fail("회원 정보가 존재하지 않습니다");
        }
        User userEntity = optionalUser.get();

        BoardReport boardReport = BoardReport.builder()
                .userId(userEntity.getId())
                .userName(userEntity.getUserName())
                .userEmail(userEntity.getEmail())
                .boardId(boardEntity.getId())
                .boardUserId(boardEntity.getUser().getId())
                .boardTitle(boardEntity.getTitle())
                .boardContents(boardEntity.getContent())
                .boardRegDate(boardEntity.getRegDate())
                .comments(boardReportInput.getComments())
                .regDate(LocalDateTime.now())
                .build();

        boardReportRepository.save(boardReport);
        return ServiceResult.success();
    }

    @Override
    public List<BoardReport> boardReportList() {
        return boardReportRepository.findAll();
    }

    @Transactional
    @Override
    public ServiceResult scrapBoard(Long id, String email) {
        Optional<Board> board = boardRepository.findById(id);
        if (!board.isPresent()) {
            return ServiceResult.fail("게시글이 존재하지 않습니다");
        }
        Board boardEntity = board.get();

        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (!optionalUser.isPresent()) {
            return ServiceResult.fail("회원 정보가 존재하지 않습니다");
        }
        User userEntity = optionalUser.get();

        BoardScrap boardScrap = BoardScrap.builder()
                .user(userEntity)
                .boardId(boardEntity.getId())
                .boardTypeId(boardEntity.getBoardType().getId())
                .boardTitle(boardEntity.getTitle())
                .boardContents(boardEntity.getContent())
                .boardRegDate(boardEntity.getRegDate())
                .regDate(LocalDateTime.now())
                .build();

        boardScrapRepository.save(boardScrap);

        return ServiceResult.success();
    }

    @Transactional
    @Override
    public ServiceResult deleteScrapBoard(Long id, String email) {
        Optional<BoardScrap> boardScrap = boardScrapRepository.findById(id);
        if (!boardScrap.isPresent()) {
            return ServiceResult.fail("삭제할 스크랩이 없습니다");
        }
        BoardScrap boardScrapEntity = boardScrap.get();

        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (!optionalUser.isPresent()) {
            return ServiceResult.fail("회원 정보가 존재하지 않습니다");
        }
        User userEntity = optionalUser.get();

        if (boardScrapEntity.getUser().getId() != userEntity.getId()) {
            return ServiceResult.fail("본인의 스크랩만 삭제할 수 있습니다");
        }

        boardScrapRepository.delete(boardScrapEntity);
        return ServiceResult.success();
    }

    private String getBoardUrl(Long boardId) {
        return String.format("/api/board/%d", boardId);
    }

    @Transactional
    @Override
    public ServiceResult addBookmark(Long id, String email) {
        Optional<Board> board = boardRepository.findById(id);
        if (!board.isPresent()) {
            return ServiceResult.fail("게시글이 존재하지 않습니다");
        }
        Board boardEntity = board.get();

        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (!optionalUser.isPresent()) {
            return ServiceResult.fail("회원 정보가 존재하지 않습니다");
        }
        User userEntity = optionalUser.get();

        BoardBookmark boardBookmark = BoardBookmark.builder()
                .user(userEntity)
                .boardId(boardEntity.getId())
                .boardTypeId(boardEntity.getBoardType().getId())
                .boardTitle(boardEntity.getTitle())
                .boardUrl(getBoardUrl(boardEntity.getId()))
                .regDate(LocalDateTime.now())
                .build();

        boardBookmarkRepository.save(boardBookmark);
        return ServiceResult.success();
    }

    @Transactional
    @Override
    public ServiceResult deleteBookmark(Long id, String email) {
        Optional<BoardBookmark> optionalBoardBookmark = boardBookmarkRepository.findById(id);
        if (!optionalBoardBookmark.isPresent()) {
            return ServiceResult.fail("삭제할 북마크가 없습니다");
        }
        BoardBookmark boardBookmarkEntity = optionalBoardBookmark.get();

        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (!optionalUser.isPresent()) {
            return ServiceResult.fail("회원 정보가 존재하지 않습니다");
        }
        User userEntity = optionalUser.get();

        if (boardBookmarkEntity.getUser().getId() != userEntity.getId()) {
            return ServiceResult.fail("본인의 스크랩만 삭제할 수 있습니다");
        }

        boardBookmarkRepository.delete(boardBookmarkEntity);
        return ServiceResult.success();
    }

    @Override
    public List<Board> postList(String email) {
        User userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new BizException("회원 정보가 존재하지 않습니다."));

        return boardRepository.findByUser(userEntity);
    }

    @Override
    public List<BoardComment> commentList(String email) {
        User userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new BizException("회원 정보가 존재하지 않습니다."));

        return boardCommentRepository.findByUser(userEntity);
    }

    @Override
    public Board detail(Long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new BizException("게시글이 존재하지 않습니다."));
    }
}
