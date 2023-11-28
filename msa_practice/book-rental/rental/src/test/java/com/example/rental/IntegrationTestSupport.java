package com.example.rental;

import com.example.rental.application.port.out.EventPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
public abstract class IntegrationTestSupport {

    @MockBean
    protected EventPort eventPort;
}
