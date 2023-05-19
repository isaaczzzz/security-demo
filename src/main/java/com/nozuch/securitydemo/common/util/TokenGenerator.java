package com.nozuch.securitydemo.common.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class TokenGenerator {

    private static final String HASH_ALGORITHM = "SHA-256";
    private static final int NUM_HASH_ITERATIONS = 10; // 哈希迭代次数

    public static String generate(String salt, String userId) {
        String timestamp = String.valueOf(new Date().getTime());

        // 将盐值、时间戳和用户ID连接起来形成一个字符串
        String tokenString = salt + timestamp + userId;

        // 多次对该字符串进行哈希处理
        String hashedToken = hashString(tokenString);
        for (int i = 0; i < NUM_HASH_ITERATIONS - 1; i++) {
            hashedToken = hashString(hashedToken);
        }

        return hashedToken;
    }

    // 对字符串进行哈希处理
    private static String hashString(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
            byte[] hashedBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));

            // 将哈希结果转换为十六进制表示形式
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();

        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException("无法对字符串进行hash: ", ex);
        }
    }
}
