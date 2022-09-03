package org.example.passwordvalidator;

import org.example.passwordvalidator.PasswordGenerator;

public class CorrectFixedPasswordGenerator implements PasswordGenerator {
    @Override
    public String generatePassword() {
        return "12345678";
    }
}
