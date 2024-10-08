package thread.cas.spinlock;

import static util.MyLogger.log;

public class SpinLockMain {

    public static void main(String[] args) {
//        SpinLockBad spinLock = new SpinLockBad();
        SpinLock spinLock = new SpinLock();

        Runnable task = () -> {
            spinLock.lock();

            try {
                // critical section
                log("비지니스 로직 실행");
            } finally {
                spinLock.unlock();
            }
        };

        Thread t1 = new Thread(task, "Thread-1");
        Thread t2 = new Thread(task, "Thread-2");

        t1.start();
        t2.start();
    }

    /*
        SpinLockBad

        2024-10-08 20:56:59.474 [ Thread-2] 락 획득 시도
        2024-10-08 20:56:59.474 [ Thread-1] 락 획득 시도
        2024-10-08 20:56:59.575 [ Thread-1] 락 획득 완료
        2024-10-08 20:56:59.575 [ Thread-1] 비지니스 로직 실행
        2024-10-08 20:56:59.575 [ Thread-1] 락 반납 완료
        2024-10-08 20:56:59.580 [ Thread-2] 락 획득 완료
        2024-10-08 20:56:59.580 [ Thread-2] 비지니스 로직 실행
        2024-10-08 20:56:59.580 [ Thread-2] 락 반납 완료

        SpinLock

        2024-10-08 21:32:08.471 [ Thread-2] 락 획득 시도
        2024-10-08 21:32:08.471 [ Thread-1] 락 획득 시도
        2024-10-08 21:32:08.472 [ Thread-2] 락 획득 완료
        2024-10-08 21:32:08.472 [ Thread-2] 비지니스 로직 실행
        2024-10-08 21:32:08.472 [ Thread-1] 락 획득 실패 - 스핀 대기
        2024-10-08 21:32:08.473 [ Thread-2] 락 반납 완료
        2024-10-08 21:32:08.473 [ Thread-1] 락 획득 완료
        2024-10-08 21:32:08.473 [ Thread-1] 비지니스 로직 실행
        2024-10-08 21:32:08.473 [ Thread-1] 락 반납 완료
     */
}
