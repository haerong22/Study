package com.example.jpablog.service;

import com.example.jpablog.dto.ReplySaveRequestDto;
import com.example.jpablog.model.Board;
import com.example.jpablog.model.User;
import com.example.jpablog.repository.BoardRepository;
import com.example.jpablog.repository.ReplyRepository;
import com.example.jpablog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;
    private final UserRepository userRepository;

    @Transactional
    public void 글쓰기(Board board, User user) {
        board.setUser(user);
        board.setCount(0);
        boardRepository.save(board);
    }

    public Page<Board> 글목록(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }

    public Board 글상세보기(Long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("글 상세보기 실패 : 아이디를 찾을 수 없습니다."));
    }

    @Transactional
    public int 글삭제하기(Long id, String name) {
        String writer = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("아이디 없음"))
                .getUser().getUsername();
        if (writer.equals(name)) {
            boardRepository.deleteById(id);
            return 1;
        }
        return -1;
    }

    @Transactional
    public int 글수정하기(Long id, Board requestBoard, String name) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("아이디 없음"));
        if (board.getUser().getUsername().equals(name)) {
            board.setTitle(requestBoard.getTitle());
            board.setContent(requestBoard.getContent());
            return 1;
        }
        return -1;
    }

    @Transactional
    public void 댓글쓰기(ReplySaveRequestDto replySaveRequestDto) {
//        User user = userRepository.findById(replySaveRequestDto.getUserId())
//                .orElseThrow(() -> new IllegalArgumentException("댓글쓰기 실패"));
//        Board board = boardRepository.findById(replySaveRequestDto.getBoardId())
//                .orElseThrow(() -> new IllegalArgumentException("댓글쓰기 실패"));
//        Reply reply = Reply.builder()
//                .user(user)
//                .board(board)
//                .content(replySaveRequestDto.getContent())
//                .build();
        replyRepository.replySave(replySaveRequestDto);
    }

    @Transactional
    public void 댓글삭제(Long replyId) {
        replyRepository.deleteById(replyId);
    }
}
