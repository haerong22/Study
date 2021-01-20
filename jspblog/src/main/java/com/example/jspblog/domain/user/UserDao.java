package com.example.jspblog.domain.user;

import com.example.jspblog.config.DB;
import com.example.jspblog.domain.user.dto.JoinReqDto;
import com.example.jspblog.domain.user.dto.LoginReqDto;

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

    public User findByUsernameAndPassword(LoginReqDto dto) {
        String sql = "select id, username, email, address from user where username=? and password=?";

        Connection conn = DB.getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        if (conn != null) {
            try {
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, dto.getUsername());
                pstmt.setString(2, dto.getPassword());
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    return User.builder()
                            .id(rs.getInt("id"))
                            .username(rs.getString("username"))
                            .email(rs.getString("email"))
                            .address(rs.getString("address"))
                            .build();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                DB.close(conn, pstmt, rs);
            }
        }

        return null;
    }
}
