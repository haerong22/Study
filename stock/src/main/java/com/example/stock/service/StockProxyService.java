package com.example.stock.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockProxyService {

    private final StockService stockService;

    public synchronized void decrease(Long id, Long quantity) {
        stockService.decrease(id, quantity);
    }
}
