package com.example.jspblog.config;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class DB {

    public static Connection getConnection() {
        try {
            Context initContext = new InitialContext();
            Context envContext  = (Context)initContext.lookup("java:/comp/env");
            DataSource ds = (DataSource)envContext.lookup("jdbc/TestDB");
            Connection conn = ds.getConnection();
            return conn;
        } catch (Exception e) {
            System.out.println("DB연결 실패");
            e.printStackTrace();
        }
        return null;
    }

    public static void close(Connection conn, PreparedStatement pstmt) {
        try {
            if (pstmt != null) pstmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
