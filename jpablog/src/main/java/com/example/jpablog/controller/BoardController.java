package com.example.jpablog.controller;

import com.example.jpablog.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    // 파라미터로 시큐리티 세션 접근 : @AuthenticationPrincipal PrincipalDetail principal
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("boards", boardService.글목록());
        return "index";
    }

    @GetMapping("/board/saveForm")
    public String save() {
        return "board/saveForm";
    }
}
