package com.example.httpencryption.utils;

import org.springframework.util.Base64Utils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.*;

public class AESUtil {

    private final String ALGORITHM = "AES/CBC/PKCS5PADDING";
    private final String KEY = "example";
    private String iv;

    public String encrypt(String data) {

        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, createKeySpec(), createIvSpec());
            byte[] encryptData = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return iv + Base64Utils.encodeToString(encryptData);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException("encrypt fail : " + e.getMessage());
        }
    }

    public String decrypt(String data) {
        String ivStr = data.substring(0,16);
        String content = data.substring(16);
        byte[] dataBytes = Base64Utils.decodeFromString(content);

        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, createKeySpec(), new IvParameterSpec(ivStr.getBytes(StandardCharsets.UTF_8)));
            byte[] original = cipher.doFinal(dataBytes);
            return new String(original, StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException("decrypt fail : " + e.getMessage());
        }
    }

    private IvParameterSpec createIvSpec() {
        try {
            String iv = StringUtil.randomStr(16);
            this.iv = iv;
            return new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            throw new RuntimeException("createIvSpec fail : " +  e.getMessage());
        }

    }

    private Key createKeySpec() {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(KEY.getBytes(StandardCharsets.UTF_8));
            return new SecretKeySpec(hashBytes, "AES");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("createKeySpec fail : " + e.getMessage());
        }
    }

}
