package com.example.jpablog.controller;

import com.example.jpablog.model.Board;
import com.example.jpablog.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    // 파라미터로 시큐리티 세션 접근 : @AuthenticationPrincipal PrincipalDetail principal
    @GetMapping("/")
    public String index(Model model,
                        @PageableDefault(size = 3, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        model.addAttribute("boards", boardService.글목록(pageable));
        return "index";
    }

    @GetMapping("/board/{id}")
    public String findById(@PathVariable Long id, Model model) {
        model.addAttribute("board", boardService.글상세보기(id));
        return "board/detail";
    }

    @GetMapping("/board/saveForm")
    public String save() {
        return "board/saveForm";
    }
}
