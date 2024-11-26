package org.example.paymentservice.checkout;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.paymentservice.order.Order;
import org.example.paymentservice.order.OrderRepository;
import org.example.paymentservice.processing.PaymentProcessingService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CheckoutController {
    private final OrderRepository orderRepository;
    private final PaymentProcessingService paymentProcessingService;

    @GetMapping("/order")
    public String order(
            @RequestParam("userId") Long userId,
            @RequestParam("courseId") Long courseId,
            @RequestParam("courseName") String courseName,
            @RequestParam("amount") String amount,
            Model model
    ) {
        Order order = new Order();
        order.setAmount(new BigDecimal(amount));
        order.setCourseId(courseId);
        order.setCourseName(courseName);
        order.setUserId(userId);
        order.setRequestId(UUID.randomUUID().toString());
        order.setStatus(Order.Status.WAIT);
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        orderRepository.save(order);

        model.addAttribute("courseName", courseName);
        model.addAttribute("requestId", order.getRequestId());
        model.addAttribute("amount", amount);
        model.addAttribute("customerKey", "customerKey-" + userId);
        return "order";
    }

    @GetMapping("/checkout")
    public String checkout() {
        return "checkout";
    }

    @GetMapping("/order-requested")
    public String orderRequested() {
        return "order-requested";
    }

    @GetMapping("/fail")
    public String fail() {
        return "fail";
    }

    @PostMapping("/confirm")
    public ResponseEntity<String> confirm(@RequestBody ConfirmRequest request) {
        Order order = orderRepository.findByRequestId(request.orderId());
        order.setUpdatedAt(LocalDateTime.now());
        order.setStatus(Order.Status.REQUESTED);
        orderRepository.save(order);

        paymentProcessingService.createPayment(request);
        return ResponseEntity.ok("confirm");
    }
}