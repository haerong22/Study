package adapter.after;

import adapter.after.security.LoginHandler;
import adapter.after.security.UserDetailsService;

public class App {

    public static void main(String[] args) {
        // 클래스 따로 생성
//        AccountService accountService = new AccountService();
//        UserDetailsService userDetailsService = new AccountUserDetailsService(accountService);
//        LoginHandler loginHandler = new LoginHandler(userDetailsService);
//        String login = loginHandler.login("kim", "kim");
//        System.out.println("login = " + login);

        UserDetailsService userDetailsService = new AccountService();
        LoginHandler loginHandler = new LoginHandler(userDetailsService);
        String login = loginHandler.login("kim", "kim");
        System.out.println("login = " + login);
    }
}
