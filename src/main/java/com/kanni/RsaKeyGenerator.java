package com.kanni;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public class RsaKeyGenerator {

    public static void main(String[] args) throws NoSuchAlgorithmException {
        // Create a KeyPairGenerator for RSA
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        
        // Initialize with key size (2048 bits is recommended)
        generator.initialize(2048);
        
        // Generate the key pair
        KeyPair keyPair = generator.generateKeyPair();
        
        // Extract public and private keys
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        
        System.out.println("Public Key: " + publicKey);
        System.out.println("Private Key: " + privateKey);
    }
}