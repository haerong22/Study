package com.example.jspblog.web;

import com.example.jspblog.domain.board.dto.CommonRespDto;
import com.example.jspblog.domain.reply.Reply;
import com.example.jspblog.domain.reply.dto.SaveReqDto;
import com.example.jspblog.service.BoardService;
import com.example.jspblog.service.ReplyService;
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
                BufferedReader br = request.getReader();
                String reqData = br.readLine();
                Gson gson = new Gson();
                SaveReqDto dto = gson.fromJson(reqData, SaveReqDto.class);
                System.out.println(dto);

                CommonRespDto<Reply> commonRespDto = new CommonRespDto<>();
                Reply reply = null;
                int result = replyService.댓글쓰기(dto);
                if (result != -1) {
                    reply = replyService.댓글찾기(result);
                    commonRespDto.setStatusCode(1);
                    commonRespDto.setData(reply);
                } else {
                    commonRespDto.setStatusCode(-1);
                }

                String responseData = gson.toJson(commonRespDto);
                Script.responseData(response, responseData);
                break;
            }
            case "delete" : {
                int id = Integer.parseInt(request.getParameter("id"));
                int result = replyService.댓글삭제(id);

                CommonRespDto<String> dto = new CommonRespDto<>();
                dto.setStatusCode(result);

                String jsonData = new Gson().toJson(dto);
                Script.responseData(response, jsonData);
            }
        }
    }
}
