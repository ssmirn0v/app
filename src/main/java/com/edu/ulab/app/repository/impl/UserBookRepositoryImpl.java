package com.edu.ulab.app.repository.impl;

import com.edu.ulab.app.entity.UserBookE;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.repository.UserBookRepository;
import com.edu.ulab.app.storage.Storage;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Repository
public class UserBookRepositoryImpl implements UserBookRepository {

    private final Storage<UserBookE> storage;

    public UserBookRepositoryImpl(Storage<UserBookE> storage) {
        this.storage = storage;
    }



    @Override
    public void deleteAllBooksByUserId(Long id) {
        storage.delete(id);
    }

    @Override
    public Optional<UserBookE> getUserBookE(Long id) {
        return storage.get(id);
    }



    @Override
    public void save(UserBookE userBookE) {
        storage.save(userBookE);
    }
}
