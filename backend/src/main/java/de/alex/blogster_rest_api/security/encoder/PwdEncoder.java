package de.alex.blogster_rest_api.security.encoder;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PwdEncoder {
    public static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
}
