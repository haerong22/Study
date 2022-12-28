package com.example.orderservice.controller;

import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.entity.OrderEntity;
import com.example.orderservice.messagequeue.KafkaProducer;
import com.example.orderservice.messagequeue.OrderProducer;
import com.example.orderservice.service.OrderService;
import com.example.orderservice.vo.RequestOrder;
import com.example.orderservice.vo.ResponseOrder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/order-service")
@RequiredArgsConstructor
public class OrderController {

    private final Environment env;
    private final OrderService orderService;
    private final KafkaProducer kafkaProducer;
    private final OrderProducer orderProducer;
    private final ModelMapper mapper = new ModelMapper();

    @PostConstruct
    public void setMapper() {
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    @PostMapping("/{userId}/orders")
    public ResponseEntity<ResponseOrder> createOrder(@PathVariable("userId") String userId,
                                                     @RequestBody RequestOrder requestOrder) {

        log.info("Before add orders data");

        OrderDto orderDto = mapper.map(requestOrder, OrderDto.class);
        orderDto.setUserId(userId);

        // jpa
        OrderDto createdOrder = orderService.createOrder(orderDto);
        ResponseOrder responseOrder = mapper.map(createdOrder, ResponseOrder.class);

        // kafka
//        orderDto.setOrderId(UUID.randomUUID().toString());
//        orderDto.setTotalPrice(requestOrder.getQty() * requestOrder.getUnitPrice());

        // send this order to the kafka
//        kafkaProducer.send("example-catalog-topic", orderDto);
//        orderProducer.send("orders", orderDto);

//        ResponseOrder responseOrder = mapper.map(orderDto, ResponseOrder.class);

        log.info("After add orders data");

        return ResponseEntity.status(HttpStatus.CREATED).body(responseOrder);
    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<ResponseOrder>> getOrders(@PathVariable("userId") String userId) {

        log.info("Before receive orders data");

        List<OrderEntity> orders = orderService.getOrdersByUserId(userId);
        List<ResponseOrder> result = new ArrayList<>();

        orders.forEach(orderEntity -> result.add(mapper.map(orderEntity, ResponseOrder.class)));

        log.info("After receive orders data");

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
