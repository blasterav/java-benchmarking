package com.phoosop.benchmarking.microbenchmarking.services;

import com.phoosop.benchmarking.microbenchmarking.configs.EncryptionConfigurations;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class EncryptionService {

    private final EncryptionConfigurations encryptionConfigurations;

    public String encrypt(String plaintext) throws Exception {
        Cipher cipher = this.initCipherGcm(Cipher.ENCRYPT_MODE);
        byte[] cipherByteArray = cipher.doFinal(plaintext.getBytes());
        return Base64.getEncoder().encodeToString(cipherByteArray);
    }

    public String decrypt(String base64CipherText) throws InvalidAlgorithmParameterException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = this.initCipherGcm(Cipher.DECRYPT_MODE);
        byte[] plainByteArray = cipher.doFinal(Base64.getDecoder().decode(base64CipherText));
        return new String(plainByteArray);
    }

    private Cipher initCipherGcm(int mode) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidAlgorithmParameterException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance(EncryptionConfigurations.CIPHER_INSTANCE_GCM);
        SecretKey key = encryptionConfigurations.initSecretKey();
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(EncryptionConfigurations.GCM_TAG_LENGTH * 8, EncryptionConfigurations.IV);
        cipher.init(mode, key, gcmParameterSpec);
        return cipher;
    }

}
