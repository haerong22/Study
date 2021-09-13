package com.example.httpencryption.utils;

import com.example.httpencryption.dto.TestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AESUtilTest {

    AESUtil util = new AESUtil();

    @Test
    void encryptTest() throws JsonProcessingException, NoSuchAlgorithmException {
        ObjectMapper objectMapper = new ObjectMapper();
        TestDto testDto = new TestDto("김", 20);

        String data = objectMapper.writeValueAsString(testDto);
        System.out.println("data = " + data);
        String encrypt = util.encrypt(data);
        System.out.println("encrypt = " + encrypt);

        String decrypt = util.decrypt(encrypt);
        System.out.println("decrypt = " + decrypt);
        TestDto origin = objectMapper.readValue(decrypt, TestDto.class);

        assertEquals(testDto.getUsername(), origin.getUsername());
        assertEquals(testDto.getAge(), origin.getAge());
    }
}