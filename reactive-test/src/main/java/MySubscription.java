import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.Iterator;

public class MySubscription implements Subscription {

    private Subscriber s;
    private Iterator<Integer> it;

    public MySubscription(Subscriber s, Iterable<Integer> it) {
        this.s = s;
        this.it = it.iterator();
    }

    @Override
    public void request(long l) {
        while(l > 0) {
            if (it.hasNext()) {
                s.onNext(it.next());
            } else {
                s.onComplete();
                break;
            }
            l--;
        }
    }

    @Override
    public void cancel() {

    }
}
