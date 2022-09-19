package com.edu.ulab.app.service;

import com.edu.ulab.app.dto.UserDto;

import java.util.Collection;
import java.util.Set;

public interface UserService {
    UserDto createUser(UserDto userDto);

    UserDto updateUser(UserDto userDto) throws Throwable;

    UserDto getUserById(Long id);

    void deleteUserById(Long id);

    void addBooksToUser(Long userId, Collection<Long> booksIds);

    Set<Long> getUserBooksIds(Long userId);
}
