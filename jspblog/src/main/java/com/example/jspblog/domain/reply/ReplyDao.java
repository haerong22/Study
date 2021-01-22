package com.example.jspblog.domain.reply;

import com.example.jspblog.config.DB;
import com.example.jspblog.domain.reply.dto.SaveReqDto;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ReplyDao {
    public int save(SaveReqDto dto) {
        String sql = "insert into reply(userId, boardId, content, createDate) values(?, ?, ?, now())";
        Connection conn = DB.getConnection();
        PreparedStatement pstmt = null;

        if (conn != null) {
            try {
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, dto.getUserId());
                pstmt.setInt(2, dto.getBoardId());
                pstmt.setString(3, dto.getContent());
                int result = pstmt.executeUpdate();
                return result;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                DB.close(conn, pstmt);
            }
        }
        return -1;
    }
}
