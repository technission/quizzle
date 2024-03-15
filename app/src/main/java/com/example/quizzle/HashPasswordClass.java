package com.example.quizzle;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashPasswordClass {

    public StringBuilder hashPassword(String Password) {
        //hash code for passwords
        StringBuilder sb;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(Password.getBytes());
            byte[] resultByteArray = messageDigest.digest();
            sb = new StringBuilder();
            for (byte b : resultByteArray) {
                sb.append(String.format("%02x", b));
            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return sb;
    }
}