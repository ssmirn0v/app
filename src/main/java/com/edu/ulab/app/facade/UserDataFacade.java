package com.edu.ulab.app.facade;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.exception.UserNotReaderException;
import com.edu.ulab.app.mapper.BookMapper;
import com.edu.ulab.app.mapper.UserMapper;
import com.edu.ulab.app.service.BookService;
import com.edu.ulab.app.service.UserService;
import com.edu.ulab.app.web.request.BookRequest;
import com.edu.ulab.app.web.request.UserBookRequest;
import com.edu.ulab.app.web.response.BookResponse;
import com.edu.ulab.app.web.response.UserBookExtendedResponse;
import com.edu.ulab.app.web.response.UserBookResponse;
import com.edu.ulab.app.web.response.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Component
public class UserDataFacade {
    private static String READER = "reader";

    private final UserService userService;
    private final BookService bookService;
    private final UserMapper userMapper;
    private final BookMapper bookMapper;

    public UserDataFacade(UserService userService,
                          BookService bookService,
                          UserMapper userMapper,
                          BookMapper bookMapper) {
        this.userService = userService;
        this.bookService = bookService;
        this.userMapper = userMapper;
        this.bookMapper = bookMapper;
    }

    public UserBookResponse createUserWithBooks(UserBookRequest userBookRequest) {
        log.info("Got user book create request: {}", userBookRequest);
        UserDto userDto = userMapper.userRequestToUserDto(userBookRequest.getUserRequest());
        log.info("Mapped user request: {}", userDto);

        if (checkIfUserIsNotReader(userDto)) {
            throw new UserNotReaderException("User did not specified that he is a reader in Title field");
        }

        UserDto createdUser = userService.createUser(userDto);
        log.info("Created user: {}", createdUser);

        List<Long> bookIdList = addBooks(createdUser.getId(), userBookRequest.getBookRequests());
        log.info("Collected book ids: {}", bookIdList);

        userService.addBooksToUser(createdUser.getId(), bookIdList);

        return UserBookResponse.builder()
                .userId(createdUser.getId())
                .booksIdList(bookIdList)
                .build();
    }

    public UserBookResponse updateUserWithBooks(Long userId, UserBookRequest userBookRequest) throws Throwable {
        UserDto userDto = userMapper.userRequestToUserDto(userBookRequest.getUserRequest());
        userDto.setId(userId);
        UserDto updatedUser = userService.updateUser(userDto);
        List<Long> bookIdList = addBooks(userId, userBookRequest.getBookRequests());
        userService.addBooksToUser(userId, bookIdList);
        return UserBookResponse.builder()
                .userId(userId)
                .booksIdList(bookIdList)
                .build();
    }

    public UserResponse getUser(Long userId) {
        UserResponse userResponse = userMapper.userDtoToUserResponse(userService.getUserById(userId));
        log.debug("RequestId: {}, retrieving user with id: {}", MDC.get("RequestId"), userId);
        return userResponse;
    }
    public UserBookExtendedResponse getUserWithBooks(Long userId) {
        UserDto user = userService.getUserById(userId);
        UserResponse userResponse = userMapper.userDtoToUserResponse(user);
        Set<Long> booksIds = userService.getUserBooksIds(userId);
        List<BookResponse> bookResponses = Stream.ofNullable(booksIds).flatMap(Collection::stream)
                .filter(Objects::nonNull)
                .map(bookService::getBookById)
                .map(bookMapper::bookDtoToBookResponse)
                .collect(Collectors.toList());

        return UserBookExtendedResponse.builder()
                .userResponse(userResponse)
                .bookResponses(bookResponses)
                .build();
    }

    public void deleteUserWithBooks(Long userId) {
        Set<Long> booksIds = userService.getUserBooksIds(userId);
        Stream.ofNullable(booksIds).flatMap(Collection::stream)
                .filter(Objects::nonNull)
                .forEach(bookService::deleteBookById);

        userService.deleteUserById(userId);

        log.debug("RequestId: {}, deleted user with id: {}", MDC.get("RequestId"), userId);
    }

    // функция чтобы привести пример создания и обработки своего исключения
    private boolean checkIfUserIsNotReader(UserDto userDto) {
        return !READER.equalsIgnoreCase(userDto.getTitle());
    }

    private List<Long> addBooks(Long userId, List<BookRequest> bookRequests) {
        return Stream.ofNullable(bookRequests).flatMap(Collection::stream)
                .filter(Objects::nonNull)
                .map(bookMapper::bookRequestToBookDto)
                .peek(bookDto -> bookDto.setUserId(userId))
                .peek(mappedBookDto -> log.info("mapped book: {}", mappedBookDto))
                .map(bookService::createBook)
                .peek(createdBook -> log.info("Created book: {}", createdBook))
                .map(BookDto::getId)
                .toList();
    }
}
