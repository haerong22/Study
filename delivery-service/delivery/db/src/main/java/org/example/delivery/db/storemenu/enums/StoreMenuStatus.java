package org.example.delivery.db.storemenu.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum StoreMenuStatus {

    REGISTERED("등록"),
    UNREGISTERED("해지"),

    ;

    private final String description;
}
