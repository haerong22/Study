package org.example.passwordvalidator;

import org.example.passwordvalidator.PasswordGenerator;

public class WrongFixedPasswordGenerator implements PasswordGenerator {
    @Override
    public String generatePassword() {
        return "12";
    }
}
