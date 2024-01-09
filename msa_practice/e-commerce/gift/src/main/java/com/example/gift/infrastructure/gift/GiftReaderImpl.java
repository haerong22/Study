package com.example.gift.infrastructure.gift;

import com.example.gift.common.exception.EntityNotFoundException;
import com.example.gift.common.exception.InvalidParamException;
import com.example.gift.domain.gift.Gift;
import com.example.gift.domain.gift.GiftReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class GiftReaderImpl implements GiftReader {
    private final GiftRepository giftRepository;

    @Override
    public Gift getGiftBy(String giftToken) {
        if (StringUtils.isEmpty(giftToken)) throw new InvalidParamException();
        return giftRepository.findByGiftToken(giftToken).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Gift getGiftByOrderToken(String orderToken) {
        if (StringUtils.isEmpty(orderToken)) throw new InvalidParamException();
        return giftRepository.findByOrderToken(orderToken).orElseThrow(EntityNotFoundException::new);
    }
}