package com.example.stock.service;

import com.example.stock.domain.Stock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StockProxyService {

    private final StockService stockService;

    public synchronized void decrease(Long id, Long quantity) {
        stockService.decrease(id, quantity);
    }
}
