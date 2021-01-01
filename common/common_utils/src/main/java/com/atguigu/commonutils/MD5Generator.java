package com.atguigu.commonutils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Generator {

    private static final String CHARSET = "UTF-8";
    private static final String MD5 = "MD5";
    private static char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6',
            '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
    public static final Log LOG = LogFactory.getLog(MD5Generator.class);

    private MessageDigest digest;

    public MD5Generator() {
        super();
        try {
            digest = MessageDigest.getInstance(MD5);
        } catch (NoSuchAlgorithmException e) {
            LOG.error("Can't create message digest!", e);
        }
    }

    public void update(byte[] data, int offset, int len) {
        if (digest == null || data == null) {
            return;
        }

        digest.update(data, offset, len);
    }

    public void update(byte[] data) {
        if (data == null) {
            return;
        }

        update(data, 0, data.length);
    }

    public String getHexMD5() {
        byte[] digestData = digest.digest();
        String hexMD5 = hexDigest(digestData);

        return hexMD5;
    }

    public byte[] getMD5() {
        return digest.digest();
    }

    public static String getHexMD5(String data) {
        try {
            MessageDigest md = MessageDigest.getInstance(MD5);
            byte[] bytes = data.getBytes(CHARSET);
            md.update(bytes);
            byte[] byteDigest = md.digest();
            return hexDigest(byteDigest).toLowerCase();
        } catch (Exception e) {
            LOG.error("Can't create message digest!", e);
        }

        return null;
    }

    public static String hexDigest(byte[] byteDigest) {
        char[] chars = new char[byteDigest.length * 2];
        for (int i = 0; i < byteDigest.length; i++) {
            // left is higher.
            chars[i * 2] = HEX_DIGITS[byteDigest[i] >> 4 & 0x0F];
            // right is lower.
            chars[i * 2 + 1] = HEX_DIGITS[byteDigest[i] & 0x0F];
        }

        return new String(chars);
    }
}
