package com.edu.ulab.app.repository.impl;

import com.edu.ulab.app.entity.UserBookE;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.repository.UserBookRepository;
import com.edu.ulab.app.storage.Storage;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Repository
public class UserBookRepositoryImpl implements UserBookRepository {

    private final Storage<UserBookE> storage;

    public UserBookRepositoryImpl(Storage<UserBookE> storage) {
        this.storage = storage;
    }


    @Override
    public Set<Long> getBooksIdsByUserId(Long id) {
        UserBookE userBookE = storage.get(id)
                .orElseThrow(() -> new NotFoundException("UserBook entity with id: " + id + " was not found"));
        return userBookE.getBooksIds();
    }

    @Override
    public void deleteAllBooksByUserId(Long id) {
        storage.delete(id);
    }

    @Override
    public void deleteUserBook(Long userId, Long bookId) {
        UserBookE userBookE = storage.get(userId)
                .orElseThrow(() -> new NotFoundException("UserBook entity with id: " + userId + " was not found"));
        userBookE.getBooksIds().remove(bookId);
    }

    @Override
    public void addAllBooksToUserId(Long userId, Collection<Long> booksIds) {
        if (storage.existsById(userId)) {
            UserBookE userBookE = storage.get(userId)
                    .orElseThrow(() -> new NotFoundException("UserBook entity with id: " + userId + " was not found"));
            userBookE.getBooksIds().addAll(booksIds);
        } else {
            UserBookE userBookE = new UserBookE(userId);
            userBookE.setBooksIds(new HashSet<>(booksIds));
            storage.save(userBookE);
        }
    }

    @Override
    public void addBookToUserId(Long userId, Long bookId) {
        UserBookE userBookE = storage.get(userId)
                .orElseThrow(() -> new NotFoundException("UserBook entity with id: " + userId + " was not found"));
        userBookE.getBooksIds().add(bookId);
    }
}
