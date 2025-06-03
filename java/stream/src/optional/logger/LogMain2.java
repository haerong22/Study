package optional.logger;

public class LogMain2 {

    public static void main(String[] args) {
        Logger logger = new Logger();
        logger.setDebug(true);
        logger.debug(value100() + value200());

        System.out.println("=== 디버그 모드 끄기 ===");
        logger.setDebug(false);
        logger.debug(value100() + value200());

        System.out.println("=== 디버그 모드 체크 ===");
        if (logger.isDebug()) {
            logger.debug(value100() + value200());
        }
    }

    static int value100() {
        System.out.println("value100 호출");
        return 100;
    }

    static int value200() {
        System.out.println("value200 호출");
        return 200;
    }

    /*
        value100 호출
        value200 호출
        [DEBUG] 300
        === 디버그 모드 끄기 ===
        value100 호출
        value200 호출
        === 디버그 모드 체크 ===
     */
}
