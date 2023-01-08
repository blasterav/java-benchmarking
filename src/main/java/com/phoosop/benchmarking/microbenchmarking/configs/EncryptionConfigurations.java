package com.phoosop.benchmarking.microbenchmarking.configs;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

@Getter
@Setter
@Validated
@Configuration
@ConfigurationProperties(prefix = "kyc.encryption")
public class EncryptionConfigurations {

    public static final String CIPHER_INSTANCE_GCM = "AES/GCM/NoPadding";
    public static final int GCM_IV_LENGTH = 12;
    public static final byte[] IV = new byte[GCM_IV_LENGTH];
    public static final int AES_KEY_SIZE = 256;
    public static final String FACTORY_INSTANCE = "PBKDF2WithHmacSHA256";
    public static final String ENCRYPTION_TYPE = "AES";
    public static final int GCM_TAG_LENGTH = 16;

    @NotEmpty
    private String secretKey;

    @NotEmpty
    private String registrationRequestPasswordSecretKey;

    @NotEmpty
    private String registrationRequestDbSecretKey;

    @NotEmpty
    private String foreignerActivityLogContentSecretKey;

    @Bean
    public SecretKey initSecretKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeySpec spec = new PBEKeySpec(this.secretKey.toCharArray(), IV, 65536, AES_KEY_SIZE);
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(FACTORY_INSTANCE);
        byte[] keyByteArray = secretKeyFactory.generateSecret(spec).getEncoded();
        return new SecretKeySpec(keyByteArray, ENCRYPTION_TYPE);
    }
}
