package com.example.jspblog.domain.reply;

import com.example.jspblog.config.DB;
import com.example.jspblog.domain.reply.dto.SaveReqDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ReplyDao {
    public int save(SaveReqDto dto) {
        String sql = "insert into reply(userId, boardId, content, createDate) values(?, ?, ?, now())";
        Connection conn = DB.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int generatedKey;
        if (conn != null) {
            try {
                pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                pstmt.setInt(1, dto.getUserId());
                pstmt.setInt(2, dto.getBoardId());
                pstmt.setString(3, dto.getContent());
                int result = pstmt.executeUpdate();
                rs = pstmt.getGeneratedKeys();
                if(rs.next()) {
                    generatedKey = rs.getInt(1);
                    if (result == 1 ) return generatedKey;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                DB.close(conn, pstmt);
            }
        }
        return -1;
    }

    public Reply findById(int id) {
        String sql = "select * from reply where id = ?";
        Connection conn = DB.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        if (conn != null) {
            try {
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, id);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    Reply reply = Reply.builder()
                            .id(rs.getInt("id"))
                            .userId(rs.getInt("userId"))
                            .boardId(rs.getInt("boardId"))
                            .content(rs.getString("content"))
                            .build();
                    return reply;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                DB.close(conn, pstmt, rs);
            }
        }
        return null;
    }

    public List<Reply> findAll(int boardId) {
        String sql = "select * from reply where boardId=? order by createDate desc";
        Connection conn = DB.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Reply> replies = new ArrayList<>();
        if (conn != null) {
            try {
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, boardId);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    Reply reply = Reply.builder()
                            .id(rs.getInt("id"))
                            .userId(rs.getInt("userId"))
                            .boardId(rs.getInt("boardId"))
                            .content(rs.getString("content"))
                            .build();
                    replies.add(reply);
                }
                return replies;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                DB.close(conn, pstmt, rs);
            }
        }
        return null;
    }

    public int deleteById(int id) {
        String sql = "delete from reply where id=?";
        Connection conn = DB.getConnection();
        PreparedStatement pstmt = null;
        if (conn != null) {
            try {
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, id);
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
