package com.edu.ulab.app.repository;

import com.edu.ulab.app.entity.BookE;

import java.util.Optional;

public interface BookRepository {

    void save(BookE book);

    Optional<BookE> getById(Long id);


    void deleteById(Long id);
}
