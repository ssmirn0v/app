package com.edu.ulab.app.repository;

import com.edu.ulab.app.entity.UserE;

import java.util.Optional;

public interface UserRepository {

    void save(UserE user);

    Optional<UserE> getById(Long id);


    void deleteById(Long id);

    boolean existsById(Long id);
}
