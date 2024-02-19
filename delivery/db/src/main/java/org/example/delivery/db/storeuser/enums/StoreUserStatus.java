package org.example.delivery.db.storeuser.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum StoreUserStatus {

    REGISTERED("등록"),
    UNREGISTERED("등록"),

    ;

    private final String description;
}
