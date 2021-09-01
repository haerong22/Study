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
import java.util.Arrays;

public class AESUtil {

    private final String ALGORITHM = "AES/CBC/PKCS5PADDING";
    private final String KEY = "example";
    private byte[] iv;

    public String encrypt(String data) {

        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, createKeySpec(), createIvSpec());
            byte[] encryptData = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
            byte[] result = new byte[iv.length + encryptData.length];
            System.arraycopy(iv, 0, result, 0, iv.length);
            System.arraycopy(encryptData, 0, result, iv.length, encryptData.length);

            return Base64Utils.encodeToString(result);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException("encrypt fail : " + e.getMessage());
        }
    }

    public String decrypt(String data) {
        byte[] dataBytes = Base64Utils.decodeFromString(data);
        byte[] iv = Arrays.copyOf(dataBytes, 16);
        byte[] encryptData = Arrays.copyOfRange(dataBytes, 16, dataBytes.length);

        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, createKeySpec(), new IvParameterSpec(iv));
            byte[] original = cipher.doFinal(encryptData);

            return new String(original, StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException("decrypt fail : " + e.getMessage());
        }
    }

    private IvParameterSpec createIvSpec() {
        try {
            byte[] iv = SecureRandom.getInstanceStrong().generateSeed(16);
            this.iv = iv;
            return new IvParameterSpec(iv);
        } catch (NoSuchAlgorithmException e) {
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
