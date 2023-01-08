package com.phoosop.benchmarking.microbenchmarking.services;

import com.phoosop.benchmarking.AbstractBenchmark;
import com.phoosop.benchmarking.microbenchmarking.configs.EncryptionConfigurations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.concurrent.TimeUnit;

@ExtendWith(MockitoExtension.class)
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class EncryptionServiceBenchmark extends AbstractBenchmark {

    @InjectMocks
    private static EncryptionService encryptionService;

    @Mock
    private static EncryptionConfigurations encryptionConfigurations;

    @BeforeEach
    public void setupEncrypt() throws NoSuchAlgorithmException, InvalidKeySpecException {
        Mockito.when(encryptionConfigurations.initSecretKey())
                .thenAnswer(invocation -> {
                    KeySpec spec = new PBEKeySpec("secret_key".toCharArray(), new byte[12], 65536, 256);
                    SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
                    byte[] keyByteArray = secretKeyFactory.generateSecret(spec).getEncoded();
                    return new SecretKeySpec(keyByteArray, "AES");
                });
    }

    @Benchmark
    public void measureEncrypt(Blackhole bh) throws Exception {
        bh.consume(encryptionService.encrypt("Encrypt this text"));
    }

    @Benchmark
    public void measureDecrypt(Blackhole bh) throws Exception {
        bh.consume(encryptionService.decrypt("kYeN0d4khlhSgAg/j2M/gteyStafnFzPr3O9mvVT5Bpd"));
    }

}
