package com.example.jspblog.web;

import com.example.jspblog.domain.reply.dto.SaveReqDto;
import com.example.jspblog.service.BoardService;
import com.example.jspblog.service.ReplyService;
import com.example.jspblog.util.Script;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/reply")
public class ReplyController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doProcess(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doProcess(request, response);
    }

    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cmd = request.getParameter("cmd");
        ReplyService replyService = new ReplyService();
        HttpSession session = request.getSession();

        switch (cmd) {
            case "save": {
                int userId = Integer.parseInt(request.getParameter("userId"));
                int boardId = Integer.parseInt(request.getParameter("boardId"));
                String content = request.getParameter("content");

                SaveReqDto dto = SaveReqDto.builder()
                        .userId(userId)
                        .boardId(boardId)
                        .content(content)
                        .build();

                int result = replyService.댓글쓰기(dto);
                if (result == 1) {
                    response.sendRedirect("/jspblog/board?cmd=detail&id=" + boardId);
                } else {
                    Script.back(response, "댓글쓰기 실패");
                }
                break;
            }
        }
    }
}
