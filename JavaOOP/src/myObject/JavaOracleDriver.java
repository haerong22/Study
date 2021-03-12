package myObject;

public class JavaOracleDriver implements DBConnect{
    @Override
    public void getConnection(String url, String user, String password) {
        System.out.println("Oracle DB에 접속합니다.");
    }
}
