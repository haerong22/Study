public class Application {
    public static void main(String[] args) {
        MyPublisher publisher = new MyPublisher();
        MySubscriber subscriber = new MySubscriber();

        publisher.subscribe(subscriber);
    }
}
