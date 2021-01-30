package com.example.jpablog.service;

import com.example.jpablog.config.auth.PrincipalDetail;
import com.example.jpablog.model.Board;
import com.example.jpablog.model.User;
import com.example.jpablog.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public void 글쓰기(Board board, User user) {
        board.setUser(user);
        board.setCount(0);
        boardRepository.save(board);
    }

    public List<Board> 글목록() {
        return boardRepository.findAll();
    }
}
