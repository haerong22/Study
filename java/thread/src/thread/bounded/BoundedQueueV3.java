package thread.bounded;

import java.util.ArrayDeque;
import java.util.Queue;

import static util.MyLogger.log;

/**
 * synchronized 블록이나 메서드에서 호출 되어야함
 *
 * wait()
 * - 현재 쓰레드가 가진 락을 반납하도 대기 상태로 변경(WAITING)
 * notify()
 * - 대기중인 쓰레드 하나를 깨움
 * notifyAll()
 * - 대기중인 쓰레드 모두 깨움
 */
public class BoundedQueueV3 implements BoundedQueue {

    private final Queue<String> queue = new ArrayDeque<>();
    private final int max;

    public BoundedQueueV3(int max) {
        this.max = max;
    }

    @Override
    public synchronized void put(String data) {

        while (queue.size() == max) {
            log("[put] 큐가 가득 참, 생산자 대기");
            try {
                wait(); // RUNNABLE -> WAITING, 락 반납
                log("[put] 생산자 깨어남");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        queue.offer(data);
        log("[put] 생산자 데이터 저장, notify() 호출");
        notify(); // 대기 쓰레드, WAITING -> BLOCKED
    }

    @Override
    public synchronized String take() {
        while (queue.isEmpty()) {
            log("[take] 큐에 데이터가 없음, 소비자 대기");
            try {
                wait(); // RUNNABLE -> WAITING, 락 반납
                log("[take] 소비자 깨어남");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        String data = queue.poll();
        log("[take] 소비자 데이터 획득, notify() 호출");
        notify(); // 대기 쓰레드, WAITING -> BLOCKED
        return data;
    }

    @Override
    public String toString() {
        return queue.toString();
    }
}
