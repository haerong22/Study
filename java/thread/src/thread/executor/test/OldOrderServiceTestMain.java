package thread.executor.test;

public class OldOrderServiceTestMain {

    public static void main(String[] args) {
        String orderNo = "Order#1234";  // 예시 주문 번호
        OldOrderService orderService = new OldOrderService();
        orderService.order(orderNo);
    }

    /*
        2024-10-12 17:21:40.305 [     main] 재고 업데이트: Order#1234
        2024-10-12 17:21:41.311 [     main] 배송 시스템 알림: Order#1234
        2024-10-12 17:21:42.313 [     main] 회계 시스템 업데이트: Order#1234
        2024-10-12 17:21:43.316 [     main] 모든 주문 처리가 성공적으로 완료되었습니다.
     */

}