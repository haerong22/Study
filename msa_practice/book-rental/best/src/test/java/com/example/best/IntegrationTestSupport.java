package com.example.best;

import com.example.best.persistence.BestBookRepository;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public abstract class IntegrationTestSupport {

    @Autowired
    protected BestBookRepository bestBookRepository;

    @AfterEach
    void clear() {
        bestBookRepository.deleteAll();
    }
}
