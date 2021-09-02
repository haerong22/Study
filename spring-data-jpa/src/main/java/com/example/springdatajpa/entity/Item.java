package com.example.springdatajpa.entity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item implements Persistable<String> {

    // @GeneratedValue 사용하지 않을 경우 -> jpa 기본전략에 의해 persist 가 아닌 merge 수행
    @Id
    private String itemId;

    public Item(String itemId) {
        this.itemId = itemId;
    }

    @CreatedDate
    private LocalDateTime createdDate;

    @Override
    public String getId() {
        return itemId;
    }

    @Override
    public boolean isNew() {
        // 새로운 객체인지 판단을 생성 날짜 유무로 한다.
        return createdDate == null;
    }
}
