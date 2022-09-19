package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.UserE;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.mapper.UserMapper;
import com.edu.ulab.app.repository.UserBookRepository;
import com.edu.ulab.app.repository.UserRepository;
import com.edu.ulab.app.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
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
        UserE user = new UserE(userDto.getFullName(), userDto.getTitle(), userDto.getAge());
        userRepository.save(user);
        userDto.setId(user.getId());
        return userDto;
    }

    @Override
    public UserDto updateUser(UserDto userDto) throws Throwable {
        UserE userE = userMapper.userDtoToUserE(userDto);
        UserDto userDtoChanged = userMapper.userEToUserDto(userRepository.update(userE));
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
        userBookRepository.addAllBooksToUserId(userId, booksIds);
    }

    @Override
    public Set<Long> getUserBooksIds(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("User with id: " + userId + " was not found");
        }
        return userBookRepository.getBooksIdsByUserId(userId);
    }


    @Override
    public void deleteUserById(Long id) {
        userBookRepository.deleteAllBooksByUserId(id);
        userRepository.deleteById(id);
    }
}
