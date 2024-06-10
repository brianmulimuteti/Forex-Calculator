package com.forexcalculator.forex.config;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderImpl implements PasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword) {
        return BCrypt.hashpw(rawPassword.toString(), BCrypt.gensalt());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return BCrypt.checkpw(rawPassword.toString(), encodedPassword);
    }
}
