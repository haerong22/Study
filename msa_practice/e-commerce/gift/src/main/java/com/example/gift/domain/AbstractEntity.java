package com.example.gift.domain;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.ZonedDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AbstractEntity {

//    @CreatedDate
    @CreationTimestamp
    private ZonedDateTime createdAt;

//    @LastModifiedDate
    @UpdateTimestamp
    private ZonedDateTime updatedAt;
}