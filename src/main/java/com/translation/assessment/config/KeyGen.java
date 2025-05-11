package com.translation.assessment.config;

import javax.crypto.KeyGenerator;
import java.util.Base64;

public class KeyGen {
    public static void main(String[] args) throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
        keyGen.init(256);
        String secret = Base64.getEncoder().encodeToString(keyGen.generateKey().getEncoded());
        System.out.println("Secret: " + secret);
    }
}