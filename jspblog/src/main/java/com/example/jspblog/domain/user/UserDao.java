package com.example.jspblog.domain.user;

import com.example.jspblog.config.DB;
import com.example.jspblog.domain.user.dto.JoinReqDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDao {

    public int save(JoinReqDto dto) { // 회원 가입
        String sql = "INSERT INTO user(username, password, email, address, userRole, createDate) VALUES (?, ?, ?, ?, 'USER', now())";

        Connection conn = DB.getConnection();
        PreparedStatement pstmt = null;
        if (conn != null) {
            try {
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, dto.getUsername());
                pstmt.setString(2, dto.getPassword());
                pstmt.setString(3, dto.getEmail());
                pstmt.setString(4, dto.getAddress());
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

    public void update() { // 회원수정

    }

    public void usernameCheck() { // 아이디 중복 체크

    }

    public void findById() { // 회원정보보기

    }

    public int findByUsername(String username) {
        String sql = "select * from user where username=?";

        Connection conn = DB.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        if (conn != null) {
            try {
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, username);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    return 1;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                DB.close(conn, pstmt, rs);
            }
        }
        return -1;
    }
}
