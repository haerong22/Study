package com.example.jspblog.domain.board;

import com.example.jspblog.config.DB;
import com.example.jspblog.domain.board.dto.DetailResDto;
import com.example.jspblog.domain.board.dto.UpdateReqDto;
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

    public List<Board> findAll(int page) {
        String sql = "select * from board order by id desc limit ?, 4";
        Connection conn = DB.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Board> boards = new ArrayList<>();

        if (conn != null) {
            try {
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, page * 4);
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

    public int count() {
        String sql = "select count(*) from board";

        Connection conn = DB.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        if (conn != null) {
            try {
                pstmt = conn.prepareStatement(sql);
                rs = pstmt.executeQuery();
                if (rs.next()) return rs.getInt(1);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                DB.close(conn, pstmt, rs);
            }
        }
        return -1;
    }

    public DetailResDto findById(int id) {
        String sql = "select b.*, u.username from board b left outer join user u on b.userId = u.id where b.id = ?";
        Connection conn = DB.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        if (conn != null) {
            try {
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, id);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    DetailResDto dto = DetailResDto.builder()
                            .id(rs.getInt("b.id"))
                            .title(rs.getString("b.title"))
                            .content(rs.getString("b.content"))
                            .readCount(rs.getInt("b.readCount"))
                            .username(rs.getString("u.username"))
                            .userId(rs.getInt("b.userId"))
                            .build();
                    return dto;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                DB.close(conn, pstmt, rs);
            }
        }
        return null;
    }

    public int updateReadCount(int id) {
        String sql = "update board set readCount=readCount+1 where id =?";
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

    public int deleteById(int id) {
        String sql = "delete from board where id=?";
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

    public int update(UpdateReqDto dto) {
        String sql = "update board set title=?, content=? where id =?";
        Connection conn = DB.getConnection();
        PreparedStatement pstmt = null;

        if (conn != null) {
            try {
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, dto.getTitle());
                pstmt.setString(2, dto.getContent());
                pstmt.setInt(3, dto.getId());
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
