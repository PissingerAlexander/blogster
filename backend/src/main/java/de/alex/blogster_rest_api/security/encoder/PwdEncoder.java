package de.alex.blogster_rest_api.security.encoder;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PwdEncoder {
    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static BCryptPasswordEncoder getEncoder() {
        return passwordEncoder;
    }
}
