package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.BookE;
import com.edu.ulab.app.entity.UserBookE;
import com.edu.ulab.app.entity.UserE;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.mapper.UserMapper;
import com.edu.ulab.app.repository.UserBookRepository;
import com.edu.ulab.app.repository.UserRepository;
import com.edu.ulab.app.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    UserBookRepository userBookRepository;
    UserMapper userMapper;


    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, UserBookRepository userBookRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.userBookRepository = userBookRepository;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        UserE user = userMapper.createUserEFromUserDto(userDto);
        userRepository.save(user);
        UserDto userDtoCreated = userMapper.userEToUserDto(user);
        return userDtoCreated;
    }

    @Override
    public UserDto updateUser(UserDto userDto) throws Throwable {
        Long id = userDto.getId();
        UserE userEForUpdate = userRepository.getById(id)
                .orElseThrow(() -> new NotFoundException("User with id: " + id + " was not found"));
        userMapper.updateUserEFromUserDto(userDto, userEForUpdate);
        UserDto userDtoChanged = userMapper.userEToUserDto(userEForUpdate);
        return userDtoChanged;
    }

    @Override
    public UserDto getUserById(Long id) {
        UserE userE = userRepository.getById(id)
                .orElseThrow(() -> new NotFoundException("User with id: " + id + " was not found"));
        UserDto userDto = userMapper.userEToUserDto(userE);
        return userDto;
    }

    @Override
    public void addBooksToUser(Long userId, Collection<Long> booksIds) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("User with id: " + userId + " was not found");
        }

        UserBookE userBookE = userBookRepository.getUserBookE(userId)
                .orElse(new UserBookE(userId));
        userBookE.getBooksIds().addAll(booksIds);
        userBookRepository.save(userBookE);
    }

    @Override
    public Set<Long> getUserBooksIds(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("User with id: " + userId + " was not found");
        }
        Set<Long> userBooksIds = userBookRepository.getUserBookE(userId)
                .map(UserBookE::getBooksIds)
                .orElse(null);
        return userBooksIds;
    }


    @Override
    public void deleteUserById(Long id) {
        userBookRepository.deleteAllBooksByUserId(id);
        userRepository.deleteById(id);
    }
}
