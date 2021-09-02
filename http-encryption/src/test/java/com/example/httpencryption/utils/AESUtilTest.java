package com.example.httpencryption.utils;

import com.example.httpencryption.dto.TestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.util.Base64Utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AESUtilTest {

    AESUtil util = new AESUtil();

    @Test
    void encryptTest() throws JsonProcessingException, NoSuchAlgorithmException {
        ObjectMapper objectMapper = new ObjectMapper();
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest("example".getBytes(StandardCharsets.UTF_8));
        System.out.println(Base64Utils.encodeToString(hashBytes));
        TestDto testDto = new TestDto("김", 20);

        String s = objectMapper.writeValueAsString(testDto);
        String test = util.encrypt(s);
        String decrypt = util.decrypt(test);
        System.out.println("test = " + test);

        assertEquals("{\"username\":\"김\",\"age\":20}", decrypt);
    }
}