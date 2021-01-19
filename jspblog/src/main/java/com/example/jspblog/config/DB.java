package com.example.jspblog.config;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;

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
}
