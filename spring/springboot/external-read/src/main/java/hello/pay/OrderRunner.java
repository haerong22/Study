package hello.pay;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderRunner implements ApplicationRunner {

    private final OrderService orderService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        orderService.order(1000);
    }
}
