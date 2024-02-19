package hello.controller;

import hello.order.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/order")
    public String order() {
        log.info("order");
        orderService.order();
        return "order";
    }

    @GetMapping("/cancel")
    public String cancel() {
        log.info("cancel");
        orderService.cancel();
        return "cancel";
    }

    @GetMapping("/stock")
    public int stock() {
        log.info("stock");
        return orderService.getStock().get();
    }
}
