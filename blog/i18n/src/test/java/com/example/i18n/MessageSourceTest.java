package com.example.i18n;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;

import java.util.Locale;

@SpringBootTest
class MessageSourceTest {

    @Autowired
    MessageSource messageSource;

    @Test
    void messageSourceTest() {
        System.out.println(messageSource.getMessage("welcome", new String[]{"kim"}, Locale.ENGLISH));
        System.out.println(messageSource.getMessage("welcome", new String[]{"kim"}, Locale.KOREAN));
    }
}
