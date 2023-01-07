package com.phoosop.benchmarking;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.TimeUnit;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class TryBBenchmark extends AbstractBenchmark{

    @InjectMocks
    private static TryB tryB;

    @Benchmark
    public void measureName(Blackhole bh) throws InterruptedException {
        tryB.init();
    }
}
