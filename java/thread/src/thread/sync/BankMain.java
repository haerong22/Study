package thread.sync;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class BankMain {

    public static void main(String[] args) throws InterruptedException {
        BankAccount account = new BankAccountV1(1000);

        Thread t1 = new Thread(new WithdrawTask(account, 800), "t1");
        Thread t2 = new Thread(new WithdrawTask(account, 800), "t2");

        t1.start();
        t2.start();

        sleep(500); // 검증 까지 잠시 대기
        log("t1 state: " + t1.getState());
        log("t2 state: " + t1.getState());

        t1.join();
        t2.join();

        log("최종 잔액: " + account.getBalance());
    }

    /*
        BankAccountV1

        2024-10-03 15:23:24.009 [       t1] 거래 시작: BankAccountV1
        2024-10-03 15:23:24.009 [       t2] 거래 시작: BankAccountV1
        2024-10-03 15:23:24.015 [       t2] [검증 시작] 출금액: 800, 잔액: 1000
        2024-10-03 15:23:24.015 [       t1] [검증 시작] 출금액: 800, 잔액: 1000
        2024-10-03 15:23:24.016 [       t2] [검증 완료] 출금액: 800, 잔액: 1000
        2024-10-03 15:23:24.016 [       t1] [검증 완료] 출금액: 800, 잔액: 1000
        2024-10-03 15:23:24.500 [     main] t1 state: TIMED_WAITING
        2024-10-03 15:23:24.501 [     main] t2 state: TIMED_WAITING
        2024-10-03 15:23:25.021 [       t2] [출금 완료] 출금액: 800, 잔액: 200
        2024-10-03 15:23:25.021 [       t1] [출금 완료] 출금액: 800, 잔액: -600
        2024-10-03 15:23:25.022 [       t1] 거래 종료
        2024-10-03 15:23:25.022 [       t2] 거래 종료
        2024-10-03 15:23:25.025 [     main] 최종 잔액: -600
     */
}
