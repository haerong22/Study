package thread.bounded;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * BlockingQueue
 * - add(e) : 요소를 큐에 추가, 가득차면 IllegalStateException
 * - remove() : 큐에서 요소를 제거하며 반환, 비어있으면 NoSuchElementException
 * - element() : 큐의 머리요소 반환, 제거X, 비어있으면 NoSuchElementException
 * - offer() : 요소를 큐에 추가, 가득차면 false 반환
 * - poll() : 큐에서 요소를 제거하며 반환, 비어있으면 null 반환
 * - peek() : 큐의 머리요소 반환, 제거X, 비어있으면 null 반환
 * - put(e) : 지정된 요소를 큐에 추가, 가득 차면 공간이 생길 때 까지 대기 (timeout 지정 가능)
 * - take() :큐에서 요소를 제거하며 반환, 비어있으면 요소가 추가 될 때까지 대기 (timeout 지정 가능)
 */
public class BoundedQueueV6_1 implements BoundedQueue {

    private final BlockingQueue<String> queue;

    public BoundedQueueV6_1(int max) {
        this.queue = new ArrayBlockingQueue<>(max);
    }

    @Override
    public void put(String data) {
        try {
            queue.put(data);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String take() {
        try {
            return queue.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return queue.toString();
    }
}
