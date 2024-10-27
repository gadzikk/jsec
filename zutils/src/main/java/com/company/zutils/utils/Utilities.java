package com.company.zutils.utils;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

@Component
@Slf4j
public class Utilities {

    @PostConstruct
    public void init() {
        var secretKey = generateSecretKey();
        var iv = generateIv();
        var encrypted = encryptAES("BARTOLEO", secretKey, iv);
        log.info("encrypted::: {}", encrypted);
        var decrypted = decryptAES(encrypted, secretKey, iv);
        log.info("decrypted::: {}", decrypted);
    }

    @SneakyThrows
    private SecretKey generateSecretKey() {
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        generator.init(128);
        return generator.generateKey();
    }
    private IvParameterSpec generateIv() {
        byte[] initializationKey = new byte[16];
        SecureRandom random = new SecureRandom();
        random.nextBytes(initializationKey);
        return new IvParameterSpec(initializationKey);
    }



    @SneakyThrows
    public byte [] encryptAES(String text, SecretKey key, IvParameterSpec iv) {
//        byte[] key = new byte[16];
//        SecureRandom random = new SecureRandom();
//        random.nextBytes(key);
//        SecretKeySpec spec = new SecretKeySpec(key, "AES");


        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        var encrypted = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
        return encrypted;
    }

    @SneakyThrows
    public String decryptAES(byte [] text, SecretKey key, IvParameterSpec iv) {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        var decrypted = cipher.doFinal(text);
        return new String(decrypted);
    }

}
