package com.hritvik.CarbonCellAssignment.utils;

import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class Base64Util {
    public static String encodeToBase64(String originalString) {
        byte[] bytesToEncode = originalString.getBytes();
        byte[] encodedBytes = Base64.getEncoder().encode(bytesToEncode);
        return new String(encodedBytes);
    }

    public static String decodeFromBase64(String encodedString) {
        byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
        return new String(decodedBytes);
    }
}
