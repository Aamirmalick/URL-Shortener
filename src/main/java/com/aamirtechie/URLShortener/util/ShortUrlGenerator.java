package com.aamirtechie.URLShortener.util;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;


public class ShortUrlGenerator {

    private static final String BASE62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final int DEFAULT_LENGTH = 6;
    private static final String ALLOWED_PATTERN = "^[a-zA-Z0-9-_]+$";
    private static final int MIN_LENGTH = 3;
    private static final int MAX_LENGTH = 10;


    public static String generateShortCodeUrl() {
        return generateShortCodeUrl(DEFAULT_LENGTH);
    }

    public static String generateShortCodeUrl(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = RANDOM.nextInt(BASE62.length());
            sb.append(BASE62.charAt(index));
        }
        return sb.toString();
    }

    public static boolean validateCustomAliasShortCodeUrl(String alias) {
        if (alias == null) return false;
        String trimmed = alias.trim();
        if (trimmed.length() < MIN_LENGTH || trimmed.length() > MAX_LENGTH) {
            return false;
        }
        return trimmed.matches(ALLOWED_PATTERN);
    }
}
