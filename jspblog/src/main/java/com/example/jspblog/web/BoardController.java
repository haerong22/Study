package com.example.jspblog.web;

import com.example.jspblog.domain.board.Board;
import com.example.jspblog.domain.board.dto.DetailResDto;
import com.example.jspblog.domain.board.dto.WriteReqDto;
import com.example.jspblog.domain.user.User;
import com.example.jspblog.service.BoardService;
import com.example.jspblog.service.UserService;
import com.example.jspblog.util.Script;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/board")
public class BoardController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doProcess(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doProcess(request, response);
    }

    protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cmd = request.getParameter("cmd");
        BoardService boardService = new BoardService();
        HttpSession session = request.getSession();
        User principal = (User) session.getAttribute("principal");
        switch (cmd) {
            case "writeForm": {
                if (principal != null) {
                    request.getRequestDispatcher("board/writeForm.jsp").forward(request, response);
                } else {
                    response.sendRedirect("user/loginForm.jsp");
                }
                break;
            }
            case "write": {
                int userId = Integer.parseInt(request.getParameter("userId"));
                String title = request.getParameter("title");
                String content = request.getParameter("content");

                WriteReqDto dto = new WriteReqDto();
                dto.setUserId(userId);
                dto.setTitle(title);
                dto.setContent(content);

                int result = boardService.글쓰기(dto);
                if (result == 1) {
                    response.sendRedirect("index.jsp");
                } else {
                    Script.back(response, "글쓰기 실패");
                }
                break;
            }
            case "list" : {
                int page = Integer.parseInt(request.getParameter("page"));
                List<Board> boards = boardService.글목록보기(page);
                int boardCount = boardService.글개수();
                int lastPage = (boardCount - 1) / 4;
                double currentPosition = (double) page / lastPage * 100;
                request.setAttribute("boards", boards);
                request.setAttribute("lastPage", lastPage);
                request.setAttribute("currentPosition", currentPosition);
                request.getRequestDispatcher("board/list.jsp").forward(request, response);
            }
            case "detail" : {
                int id = Integer.parseInt(request.getParameter("id"));
                DetailResDto dto = boardService.글상세보기(id);
                if (dto == null) {
                    Script.back(response, "상세보기에 실패하였습니다.");
                } else {
                    request.setAttribute("detail", dto);
                    request.getRequestDispatcher("board/detail.jsp").forward(request, response);
                }

            }
        }
    }
}
