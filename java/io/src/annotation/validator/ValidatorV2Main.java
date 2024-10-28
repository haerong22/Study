package annotation.validator;

import static util.MyLogger.log;

public class ValidatorV2Main {

    public static void main(String[] args) {
        User user = new User("user1", 0);
        Team team = new Team("", 0);

        try {
            log("== user 검증 ==");
            Validator.validate(user);
        } catch (Exception e) {
            log(e);
        }

        try {
            log("== team 검증 ==");
            Validator.validate(team);
        } catch (Exception e) {
            log(e);
        }
    }

    /*
        00:02:34.946 [     main] == user 검증 ==
        00:02:34.974 [     main] java.lang.RuntimeException: 나이는 1과 100사이어야 합니다.
        00:02:34.974 [     main] == team 검증 ==
        00:02:34.975 [     main] java.lang.RuntimeException: 이름이 비었습니다.
     */
}
