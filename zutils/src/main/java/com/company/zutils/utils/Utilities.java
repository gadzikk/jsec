package com.company.zutils.utils;

import jakarta.xml.bind.DatatypeConverter;
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
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

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

        var encrypted_B64 = encryptAES_B64("BARTOLEO", secretKey, iv);
        log.info("encryptedAES_B64::: {}", encrypted_B64);
        var decrypted_B64 = decryptAES_B64(encrypted_B64, secretKey, iv);
        log.info("decryptedAES_B64::: {}", decrypted_B64);

        var md5hash = md5("BARTOLEO");
        log.info("MD5::: {}", md5hash);
        var sha512hash =  sha512("BARTOLEO");
        log.info("SHA-512::: {}", sha512hash);
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

    @SneakyThrows
    public byte [] encryptAES_B64(String text, SecretKey key, IvParameterSpec iv) {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        var cipheredText = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encode(cipheredText);
    }

    @SneakyThrows
    public String decryptAES_B64(byte [] text, SecretKey key, IvParameterSpec iv) {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        var decoded = Base64.getDecoder().decode(text);
        var decrypted = cipher.doFinal(decoded);
        return new String(decrypted);
    }

    @SneakyThrows
    public String md5(String text) {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(text.getBytes());
        byte [] digest = md.digest();
        return DatatypeConverter.printHexBinary(digest);
    }

    @SneakyThrows
    public String sha512(String text) {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(text.getBytes());
        byte [] digest = md.digest();
        return DatatypeConverter.printHexBinary(digest);
    }

}
