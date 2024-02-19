package org.example.delivery.db.store.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum StoreStatus {

    REGISTERED("등록"),
    UNREGISTERED("해지"),

    ;

    private final String description;
}
