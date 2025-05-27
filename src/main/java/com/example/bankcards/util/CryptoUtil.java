package com.example.bankcards.util;

import jakarta.annotation.PostConstruct;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Component;

@Component
public class CryptoUtil {

    @Value("${token.crypto.password}")
    private String pass;

    @Value("${token.crypto.salt}")
    private String salt;

    private TextEncryptor text;

    @SneakyThrows
    @PostConstruct
    public void init() {
        text = Encryptors.text(pass, salt);
    }

    @SneakyThrows
    public String encrypt(String plainText){
        return text.encrypt(plainText);
    }

    @SneakyThrows
    public String decrypt(String cipherText) {
        return text.decrypt(cipherText);
    }
}
