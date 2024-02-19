package com.example.restcontroller.util;

import lombok.experimental.UtilityClass;
import org.springframework.security.crypto.bcrypt.BCrypt;

@UtilityClass
public class PasswordUtils {

    public static String encryptedPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean equalPassword(String password, String encryptedPassword) {
        return BCrypt.checkpw(password, encryptedPassword);
    }
}
