package thread.sync;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

/**
 * synchronized 키워드 사용 시 RUNNABLE -> BLOCKED 로 상태 변경
 * - 락이 풀릴 때 까지 무한 대기
 * - 타임아웃 X, 인터럽트 X
 * - 락이 풀려도 어떤 쓰레드가 락을 획득 할 지 알 수 없음
 */
public class BankAccountV2 implements BankAccount {

    private int balance;

    public BankAccountV2(int initBalance) {
        this.balance = initBalance;
    }

    @Override
    public synchronized boolean withdraw(int amount) {
        log("거래 시작: " + getClass().getSimpleName());

        // === 임계영역 시작 ===
        // 검증
        log("[검증 시작] 출금액: " + amount + ", 잔액: " + balance);
        if (balance < amount) {
            log("[검증 실패] 출금액: " + amount + ", 잔액: " + balance);
            return false;
        }

        // 출금
        log("[검증 완료] 출금액: " + amount + ", 잔액: " + balance);
        sleep(1000);
        balance = balance - amount;
        log("[출금 완료] 출금액: " + amount + ", 잔액: " + balance);
        // === 임계영역 종료 ===

        log("거래 종료");
        return true;
    }

    @Override
    public synchronized int getBalance() {
        return balance;
    }
}
