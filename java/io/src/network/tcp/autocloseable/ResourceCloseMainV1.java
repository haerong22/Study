package network.tcp.autocloseable;

public class ResourceCloseMainV1 {

    public static void main(String[] args) {
        try {
            logic();
        } catch (CallException e) {
            System.out.println("CallException 예외 처리");
            throw new RuntimeException(e);
        } catch (CloseException e) {
            System.out.println("CloseException 예외 처리");
            throw new RuntimeException(e);
        }
    }

    /*
        resource1 call
        resource2 callEx
        CallException 예외 처리
        Exception in thread "main" java.lang.RuntimeException: network.tcp.autocloseable.CallException: resource2 ex
     */

    private static void logic() throws CallException, CloseException {
        ResourceV1 resource1 = new ResourceV1("resource1");
        ResourceV1 resource2 = new ResourceV1("resource2");

        resource1.call();
        resource2.callEx(); // CallException

        System.out.println("자원 정리"); // 호출 X
        resource2.closeEx();
        resource1.close();
    }
}
