package com.edu.ulab.app.repository;

import java.util.Collection;
import java.util.Set;

public interface UserBookRepository {

    Set<Long> getBooksIdsByUserId(Long id);

    void deleteAllBooksByUserId(Long id);

    void deleteUserBook(Long userId, Long bookId);

    void addAllBooksToUserId(Long userId, Collection<Long> booksIds);

    void addBookToUserId(Long userId, Long bookId);
}
