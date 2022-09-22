package com.edu.ulab.app.repository;

import com.edu.ulab.app.entity.UserBookE;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface UserBookRepository {

    void deleteAllBooksByUserId(Long id);


    Optional<UserBookE> getUserBookE(Long id);

    public void save(UserBookE userBookE);
}
