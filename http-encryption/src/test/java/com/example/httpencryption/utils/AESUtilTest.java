package com.example.httpencryption.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AESUtilTest {

    AESUtil util = new AESUtil();

    @Test
    void encryptTest() {
        String hello = util.encrypt("hello");

        System.out.println("hello = " + hello);

        String decrypt = util.decrypt(hello);

        assertEquals("hello", decrypt);
    }
}