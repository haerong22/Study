package network.tcp.autocloseable;

public class ResourceCloseMainV4 {

    public static void main(String[] args) {
        try {
            logic();
        } catch (CallException e) {
            System.out.println("CallException 예외 처리");

            // 자원 정리 시의 부가예외는 핵심 예외에 담아서 응답
            Throwable[] suppressed = e.getSuppressed();
            for (Throwable throwable : suppressed) {
                System.out.println("suppressedEx = " + throwable);
            }

            throw new RuntimeException(e);
        } catch (CloseException e) {
            System.out.println("CloseException 예외 처리");
            throw new RuntimeException(e);
        }
    }

    /*
        resource1 call
        resource2 callEx
        resource2 close
        resource1 close
        ex: network.tcp.autocloseable.CallException: resource2 ex
        CallException 예외 처리
        suppressedEx = network.tcp.autocloseable.CloseException: resource2 ex
        suppressedEx = network.tcp.autocloseable.CloseException: resource1 ex
        Exception in thread "main" java.lang.RuntimeException: network.tcp.autocloseable.CallException: resource2 ex
            at network.tcp.autocloseable.ResourceCloseMainV4.main(ResourceCloseMainV4.java:17)
        Caused by: network.tcp.autocloseable.CallException: resource2 ex
            at network.tcp.autocloseable.ResourceV2.callEx(ResourceV2.java:17)
            at network.tcp.autocloseable.ResourceCloseMainV4.logic(ResourceCloseMainV4.java:53)
            at network.tcp.autocloseable.ResourceCloseMainV4.main(ResourceCloseMainV4.java:7)
            Suppressed: network.tcp.autocloseable.CloseException: resource2 ex
                at network.tcp.autocloseable.ResourceV2.close(ResourceV2.java:23)
                at network.tcp.autocloseable.ResourceCloseMainV4.logic(ResourceCloseMainV4.java:48)
                ... 1 more
            Suppressed: network.tcp.autocloseable.CloseException: resource1 ex
                at network.tcp.autocloseable.ResourceV2.close(ResourceV2.java:23)
                at network.tcp.autocloseable.ResourceCloseMainV4.logic(ResourceCloseMainV4.java:48)
                ... 1 more
     */

    private static void logic() throws CallException, CloseException {
        try (
                ResourceV2 resource1 = new ResourceV2("resource1");
                ResourceV2 resource2 = new ResourceV2("resource2");
        ) {
            resource1.call();
            resource2.callEx(); // CallException
        } catch (CallException e) {
            System.out.println("ex: " + e);
            throw e;
        }
    }
}
