package com.example.jspblog.domain.board;

import com.example.jspblog.config.DB;
import com.example.jspblog.domain.board.dto.WriteReqDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BoardDao {
    public int write(WriteReqDto dto) {

        String sql = "insert into board(userId, title, content, createDate) values(?, ?, ?, now())";
        Connection conn = DB.getConnection();
        PreparedStatement pstmt = null;

        if (conn != null) {
            try {
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, dto.getUserId());
                pstmt.setString(2, dto.getTitle());
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
