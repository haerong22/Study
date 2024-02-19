package org.example.delivery.db.userorder.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UserOrderStatus {

    REGISTERED("등록"),
    UNREGISTERED("해지"),
    ORDER("주문"),
    ACCEPT("확인"),
    COOKING("요리중"),
    DELIVERY("배달중"),
    RECEIVE("완료"),

    ;

    private final String description;
}
