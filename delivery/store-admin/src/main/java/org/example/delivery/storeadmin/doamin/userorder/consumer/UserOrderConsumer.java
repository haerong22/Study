package org.example.delivery.storeadmin.doamin.userorder.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.delivery.common.message.model.UserOrderMessage;
import org.example.delivery.storeadmin.doamin.userorder.business.UserOrderBusiness;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserOrderConsumer {

    private final UserOrderBusiness userOrderBusiness;

    @RabbitListener(queues = "delivery.queue")
    public void userOrderConsumer(UserOrderMessage userOrderMessage) {
        log.info("message queue >> {}", userOrderMessage);
        userOrderBusiness.pushUserOrder(userOrderMessage);
    }

}
