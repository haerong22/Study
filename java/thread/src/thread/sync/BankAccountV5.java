package thread.sync;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static util.MyLogger.log;
import static util.ThreadUtils.sleep;

public class BankAccountV5 implements BankAccount {

    private int balance;

    private final Lock lock = new ReentrantLock();

    public BankAccountV5(int initBalance) {
        this.balance = initBalance;
    }

    @Override
    public boolean withdraw(int amount) {
        log("거래 시작: " + getClass().getSimpleName());

        if (!lock.tryLock()) { // 락을 획득하지 못하면 대기 없이 false 반환
            log("[진입 실패] 이미 처리중인 작업이 있습니다.");
            return false;
        }

        try {
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
        } finally {
            lock.unlock(); // lock 해제 (반드시 호출)
        }

        log("거래 종료");
        return true;
    }

    @Override
    public int getBalance() {
        lock.lock(); // lock 획득
        try {
            return balance;
        } finally {
            lock.unlock();  // lock 해제 (반드시 호출)
        }
    }
}
