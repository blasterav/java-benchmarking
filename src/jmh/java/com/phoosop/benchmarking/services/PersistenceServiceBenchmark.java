package com.phoosop.benchmarking.services;

import com.phoosop.benchmarking.AbstractBenchmark;
import com.phoosop.benchmarking.model.entities.UserEntity;
import com.phoosop.benchmarking.repositories.Repository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.stubbing.answers.AnswersWithDelay;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@ExtendWith(MockitoExtension.class)
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class PersistenceServiceBenchmark extends AbstractBenchmark {

    @InjectMocks
    private static PersistenceService persistenceService;

    @Mock
    private static Repository repository;

    @BeforeEach
    public void setupMeasureFindFirstById() {
        Mockito.when(repository.findFirstById(13))
                .thenAnswer(new AnswersWithDelay(50, invocation -> Optional.of(new UserEntity()
                        .setId(13L)
                        .setFirstName("FirstName")
                        .setSecondName("LastName")
                        .setPassportNumber("72647482743")
                        .setAge(34))));
    }

    @BeforeEach
    public void setupMeasureFindAllByFirstName() {
        ArrayList<UserEntity> list = new ArrayList<>();
        for (long i = 1; i < 1000000; i++) {
            list.add(new UserEntity()
                    .setId(i)
                    .setFirstName("firstName")
                    .setSecondName("LastName")
                    .setPassportNumber("72647482743")
                    .setAge(34));
        }

        Mockito.when(repository.findAllByFirstName("firstName"))
                .thenAnswer(new AnswersWithDelay(50, invocation -> list));
    }

    @Benchmark
    public void measureFindFirstById(Blackhole bh) {
        bh.consume(persistenceService.findFirstById(13));
    }

    @Benchmark
    public void measureFindAllByFirstName(Blackhole bh) {
        bh.consume(persistenceService.findAllByFirstName("firstName"));
    }
}
