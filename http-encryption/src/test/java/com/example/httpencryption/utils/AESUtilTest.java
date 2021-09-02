package com.example.httpencryption.utils;

import com.example.httpencryption.dto.TestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AESUtilTest {

    AESUtil util = new AESUtil();

    @Test
    void encryptTest() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        TestDto testDto = new TestDto("kim", 20);

        String s = objectMapper.writeValueAsString(testDto);

        String test = util.encrypt(s);

        System.out.println("hello = " + test);

        String decrypt = util.decrypt(test);

        assertEquals("{\"username\":\"kim\",\"age\":20}", decrypt);
    }
}