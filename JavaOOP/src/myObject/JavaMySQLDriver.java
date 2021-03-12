package myObject;

public class JavaMySQLDriver implements DBConnect{
    @Override
    public void getConnection(String url, String user, String password) {
        System.out.println("MySQL DB에 접속합니다.");
    }
}
