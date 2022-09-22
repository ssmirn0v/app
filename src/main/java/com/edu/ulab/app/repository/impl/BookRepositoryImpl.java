package com.edu.ulab.app.repository.impl;

import com.edu.ulab.app.entity.BookE;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.repository.BookRepository;
import com.edu.ulab.app.storage.Storage;
import org.springframework.stereotype.Repository;

import static com.edu.ulab.app.util.SetterHelper.setIfNotNull;

import java.util.Optional;

@Repository
public class BookRepositoryImpl implements BookRepository {

    private final Storage<BookE> storage;

    public BookRepositoryImpl(Storage<BookE> storage) {
        this.storage = storage;
    }

    @Override
    public void save(BookE bookE) {
        storage.save(bookE);
    }

    @Override
    public Optional<BookE> getById(Long id) {
        return storage.get(id);
    }


    @Override
    public void deleteById(Long id) {
        storage.delete(id);
    }
}
