package com.atguigu.vod.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Signer {

    private static char[] HEX_DIGITS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public Signer() {
    }

    public static String calculateSign(String action, String secret, String corpCode, String usernName, Long timestamp) {
        String signingText = secret + "|" + action + "|" + corpCode + "|" + usernName + "|" + timestamp + "|" + secret;
        return md5(signingText);
    }

    public static String md5(String input) {
        MessageDigest md = null;

        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException var4) {
            var4.printStackTrace();
        }

        try {
            md.update(input.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException var3) {
            var3.printStackTrace();
        }

        byte[] byteDigest = md.digest();
        return toHexString(byteDigest);
    }

    public static String toHexString(byte[] byteDigest) {
        char[] chars = new char[byteDigest.length * 2];

        for(int i = 0; i < byteDigest.length; ++i) {
            chars[i * 2] = HEX_DIGITS[byteDigest[i] >> 4 & 15];
            chars[i * 2 + 1] = HEX_DIGITS[byteDigest[i] & 15];
        }

        return new String(chars);
    }

    public static void main(String... args) {
        String secretMd5 = md5("hello");
        System.out.println(secretMd5);
    }
}
