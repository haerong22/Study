package com.example.jspblog.domain.board;

import com.example.jspblog.config.DB;
import com.example.jspblog.domain.board.dto.WriteReqDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public List<Board> findAll() {
        String sql = "select * from board order by id desc";
        Connection conn = DB.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Board> boards = new ArrayList<>();

        if (conn != null) {
            try {
                pstmt = conn.prepareStatement(sql);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    Board board = Board.builder()
                            .id(rs.getInt("id"))
                            .title(rs.getString("title"))
                            .content(rs.getString("content"))
                            .readCount(rs.getInt("readCount"))
                            .userId(rs.getInt("userId"))
                            .createDate(rs.getTimestamp("createDate"))
                            .build();
                    boards.add(board);
                }
                return boards;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                DB.close(conn, pstmt, rs);
            }
        }
        return null;
    }
}
