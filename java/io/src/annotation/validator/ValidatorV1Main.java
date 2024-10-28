package annotation.validator;

import static util.MyLogger.log;

public class ValidatorV1Main {

    public static void main(String[] args) {
        User user = new User("user1", 0);
        Team team = new Team("", 0);

        try {
            log("== user 검증 ==");
            validateUser(user);
        } catch (Exception e) {
            log(e);
        }

        try {
            log("== team 검증 ==");
            validateTeam(team);
        } catch (Exception e) {
            log(e);
        }
    }

    /*
        23:54:29.075 [     main] == user 검증 ==
        23:54:29.076 [     main] java.lang.RuntimeException: 나이는 1과 100사이어야 합니다.
        23:54:29.076 [     main] == team 검증 ==
        23:54:29.076 [     main] java.lang.RuntimeException: 이름이 비었습니다.
     */

    private static void validateUser(User user) {
        if (user.getName() == null || user.getName().isEmpty()) {
            throw new RuntimeException("이름이 비었습니다.");
        }
        if (user.getAge() < 1 || user.getAge() > 100) {
            throw new RuntimeException("나이는 1과 100사이어야 합니다.");
        }
    }

    private static void validateTeam(Team team) {
        if (team.getName() == null || team.getName().isEmpty()) {
            throw new RuntimeException("이름이 비었습니다.");
        }
        if (team.getMemberCount() < 1 || team.getMemberCount() > 999) {
            throw new RuntimeException("회원 수는 1과 999사이어야 합니다.");
        }
    }
}
