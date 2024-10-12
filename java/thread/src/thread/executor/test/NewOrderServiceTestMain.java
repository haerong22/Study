package thread.executor.test;

public class NewOrderServiceTestMain {

    public static void main(String[] args) throws Exception {
        String orderNo = "Order#1234";  // 예시 주문 번호
        NewOrderService orderService = new NewOrderService();
        orderService.order(orderNo);
    }

    /*
        2024-10-12 17:28:12.249 [pool-1-thread-1] 재고 업데이트: Order#1234
        2024-10-12 17:28:12.249 [pool-1-thread-2] 배송 시스템 알림: Order#1234
        2024-10-12 17:28:12.249 [pool-1-thread-3] 회계 시스템 업데이트: Order#1234
        2024-10-12 17:28:13.256 [     main] 모든 주문 처리가 성공적으로 완료되었습니다.
     */

}