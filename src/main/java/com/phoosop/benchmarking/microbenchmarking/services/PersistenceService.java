package com.phoosop.benchmarking.microbenchmarking.services;

import com.phoosop.benchmarking.microbenchmarking.model.commands.UserCommand;
import com.phoosop.benchmarking.microbenchmarking.repositories.Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PersistenceService {

    private final Repository repository;

    public Optional<UserCommand> findFirstById(long id) {
        return repository.findFirstById(id)
                .map(item -> new UserCommand()
                        .setId(item.getId())
                        .setFirstName(item.getFirstName())
                        .setSecondName(item.getSecondName())
                        .setPassportNumber(item.getPassportNumber())
                        .setAge(item.getAge()));
    }

    public List<UserCommand> findAllByFirstName(String firstName) {
        return repository.findAllByFirstName(firstName).stream()
                .map(item -> new UserCommand()
                        .setId(item.getId())
                        .setFirstName(item.getFirstName())
                        .setSecondName(item.getSecondName())
                        .setPassportNumber(item.getPassportNumber())
                        .setAge(item.getAge()))
                .collect(Collectors.toList());
    }

}
