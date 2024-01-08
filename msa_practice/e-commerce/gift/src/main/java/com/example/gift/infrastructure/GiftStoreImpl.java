package com.example.gift.infrastructure;

import com.example.gift.common.exception.InvalidParamException;
import com.example.gift.domain.gift.Gift;
import com.example.gift.domain.gift.GiftStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class GiftStoreImpl implements GiftStore {
    private final GiftRepository giftRepository;

    @Override
    public Gift store(Gift gift) {
        if (gift == null) throw new InvalidParamException();
        return giftRepository.save(gift);
    }
}