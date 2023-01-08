package com.phoosop.benchmarking.microbenchmarking.repositories;

import com.phoosop.benchmarking.microbenchmarking.model.entities.UserEntity;

import java.util.List;
import java.util.Optional;

public interface Repository {

    Optional<UserEntity> findFirstById(long id);

    List<UserEntity> findAllByFirstName(String firstName);

}
