package com.farhan.bsfnet.util;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import static org.apache.tomcat.util.security.ConcurrentMessageDigest.digest;

public class MD5Util {

    private static final Charset UTF_8 = StandardCharsets.UTF_8;

    private static byte[] digest(byte[] input) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
        byte[] result = md.digest(input);
        return result;
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
    
    public static String generate(String input) {
        byte[] md5InBytes = digest(input.getBytes(UTF_8));
        return bytesToHex(md5InBytes);
    }

}
