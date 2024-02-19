package chain_of_responsibility.before;

public class RequestHandler {

    public void handler(Request request) {
        System.out.println(request.getBody());
    }
}
