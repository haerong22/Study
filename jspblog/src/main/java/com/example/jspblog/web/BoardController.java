package com.example.jspblog.web;

import com.example.jspblog.domain.board.Board;
import com.example.jspblog.domain.board.dto.*;
import com.example.jspblog.domain.reply.Reply;
import com.example.jspblog.domain.user.User;
import com.example.jspblog.service.BoardService;
import com.example.jspblog.service.ReplyService;
import com.example.jspblog.service.UserService;
import com.example.jspblog.util.Script;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
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
        ReplyService replyService = new ReplyService();
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
                break;
            }
            case "detail" : {
                int id = Integer.parseInt(request.getParameter("id"));
                DetailResDto dto = boardService.글상세보기(id);
                List<Reply> replies = replyService.댓글목록보기(id);

                if (dto == null) {
                    Script.back(response, "상세보기에 실패하였습니다.");
                } else {
                    request.setAttribute("detail", dto);
                    request.setAttribute("replies", replies);
                    request.getRequestDispatcher("board/detail.jsp").forward(request, response);
                }
                break;
            }
            case "delete" : {
                BufferedReader br = request.getReader();
                String data = br.readLine();
                Gson gson = new Gson();
                DeleteReqDto dto = gson.fromJson(data, DeleteReqDto.class);
                CommonRespDto<String> commonRespDto = new CommonRespDto<>();

                int result = boardService.글삭제(dto.getBoardId());
                commonRespDto.setStatusCode(result);
                String resData = gson.toJson(commonRespDto);
                PrintWriter out = response.getWriter();
                out.print(resData);
                out.flush();
                break;
            }
            case "updateForm" : {
                int id = Integer.parseInt(request.getParameter("id"));
                DetailResDto dto = boardService.글상세보기(id);
                request.setAttribute("detail", dto);
                request.getRequestDispatcher("board/updateForm.jsp").forward(request, response);
                break;
            }
            case "update" : {
                int id = Integer.parseInt(request.getParameter("id"));
                String title = request.getParameter("title");
                String content = request.getParameter("content");

                UpdateReqDto dto = UpdateReqDto.builder()
                        .id(id)
                        .title(title)
                        .content(content)
                        .build();

                int result = boardService.글수정(dto);
                if (result == 1) {
                    response.sendRedirect("/jspblog/board?cmd=detail&id=" + id);
                } else {
                    Script.back(response, "글 수정 실패");
                }
            }
        }
    }
}
