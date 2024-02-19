package org.example.delivery.api.domain.userorder.producer;

import lombok.RequiredArgsConstructor;
import org.example.delivery.api.common.rabbitmq.Producer;
import org.example.delivery.common.message.model.UserOrderMessage;
import org.example.delivery.db.userorder.UserOrderEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserOrderProducer {

    private final Producer producer;

    private static final String EXCHANGE = "delivery.exchange";
    private static final String ROUTE_KEY = "delivery.key";

    public void sendOrder(UserOrderEntity userOrderEntity) {
        sendOrder(userOrderEntity.getId());
    }

    public void sendOrder(Long userOrderId) {
        UserOrderMessage message = UserOrderMessage.builder()
                .userOrderId(userOrderId)
                .build();

        producer.producer(EXCHANGE, ROUTE_KEY, message);
    }
}
