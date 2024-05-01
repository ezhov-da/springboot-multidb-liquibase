package com.aziz.multidbs.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Service
public class EncryptionServiceImpl implements EncryptionService {
    private final String publicKey;

    private final String privateKey;

    public EncryptionServiceImpl(@Value("${public-key}") String publicKey,
                                 @Value("${private-key}") String privateKey) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    @Override
    public String encrypt(String text) throws InvalidKeySpecException,
                                              NoSuchAlgorithmException,
                                              NoSuchPaddingException,
                                              InvalidKeyException,
                                              IllegalBlockSizeException,
                                              BadPaddingException {
        byte[] publicKeyBytes = Base64.getDecoder().decode(publicKey.getBytes());
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
        PublicKey publicKey =  keyFactory.generatePublic(publicKeySpec);

        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return Base64.getEncoder().encodeToString(cipher.doFinal(text.getBytes(StandardCharsets.UTF_8)));
    }

    @Override
    public String decrypt(String encrypted) throws NoSuchAlgorithmException,
                                                   InvalidKeySpecException,
                                                   NoSuchPaddingException,
                                                   InvalidKeyException,
                                                   IllegalBlockSizeException,
                                                   BadPaddingException {
        byte[] privateKeyBytes = Base64.getDecoder().decode(privateKey.getBytes());
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey= keyFactory.generatePrivate(privateKeySpec);
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return new String(cipher.doFinal(Base64.getDecoder().decode(encrypted)), StandardCharsets.UTF_8);
    }
}
