package org.example.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.Random;

public class DummyMemberDataGenerator {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/bobby_pay";
    private static final String DB_USER = "mysqluser";
    private static final String DB_PASSWORD = "mysqlpw";

    private static final String[] ADDRESSES = {"강남구", "관악구", "서초구"};

    public static void main(String[] args) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

//            generateDummyMembershipData(conn);

            generateDummyPaymentData(conn);

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void generateDummyMembershipData(Connection conn) throws SQLException {
        String insertQuery = "INSERT INTO membership(membership_id, address, email, is_corp, is_valid, name) VALUES(?, ?, ?, ?, ?, ?)";

        Random random = new Random();

        PreparedStatement pstmt = conn.prepareStatement(insertQuery);

        int numberOfDummyData = 10000;

        for (int i = 1; i <= numberOfDummyData; i++) {
            pstmt.setLong(1, i);
            pstmt.setString(2, ADDRESSES[random.nextInt(ADDRESSES.length)]);
            pstmt.setString(3, "email_" + i + "@example.com");
            pstmt.setBoolean(4, random.nextBoolean());
            pstmt.setBoolean(5, random.nextBoolean());
            pstmt.setString(6, "User " + i);

            pstmt.executeUpdate();
        }

        pstmt.close();
    }

    private static void generateDummyPaymentData (Connection conn) throws SQLException {
        Random random = new Random();

        try {
            String query = "INSERT INTO payment (payment_id, request_membership_id, request_price, franchise_id, franchise_fee_rate, payment_status, approved_at) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(query);

            int numberOfTestData = 100;
            for (int i = 1; i <= numberOfTestData; i++) {
                // 랜덤 값 생성
                String membershipId = "" + (random.nextInt(900) + 100); // 100 ~ 999
                int price = (random.nextInt(9) + 1) * 1000; // 1000 ~ 9000
                String franchiseId =  "" + (random.nextInt(10) + 1L);
                String franchiseFeeRate = String.format("%.2f", random.nextDouble() * 5.0);
                int paymentStatus = i % 2;
                Date approvedAt = new Date(System.currentTimeMillis() - random.nextInt(10000000));

                preparedStatement.setLong(1, i);
                preparedStatement.setString(2, membershipId);
                preparedStatement.setInt(3, price);
                preparedStatement.setString(4, franchiseId);
                preparedStatement.setString(5, franchiseFeeRate);
                preparedStatement.setInt(6, paymentStatus);
                preparedStatement.setDate(7, new java.sql.Date(approvedAt.getTime()));
                preparedStatement.executeUpdate();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.close();
        }
    }
}
