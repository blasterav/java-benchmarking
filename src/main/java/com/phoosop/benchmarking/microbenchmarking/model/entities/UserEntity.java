package com.phoosop.benchmarking.microbenchmarking.model.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class UserEntity {

    private Long id;
    private String firstName;
    private String secondName;
    private Integer age;
    private String passportNumber;

}
