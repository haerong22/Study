import myObject.DBConnect;
import myObject.JavaMySQLDriver;
import myObject.JavaOracleDriver;

public class Java26 {
    public static void main(String[] args) {

        // Oracle, MySQL 등을 사용하기 위해 Driver class 가 필요
        DBConnect conn = new JavaOracleDriver();
        conn.getConnection("url", "id", "password");

        conn = new JavaMySQLDriver();
        conn.getConnection("url", "id", "password");
    }
}
