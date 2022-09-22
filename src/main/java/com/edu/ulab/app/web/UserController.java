package com.edu.ulab.app.web;

import com.edu.ulab.app.facade.UserDataFacade;
import com.edu.ulab.app.web.constant.WebConstant;
import com.edu.ulab.app.web.request.UserBookRequest;
import com.edu.ulab.app.web.response.UserBookExtendedResponse;
import com.edu.ulab.app.web.response.UserBookResponse;
import com.edu.ulab.app.web.response.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Pattern;

import static com.edu.ulab.app.web.constant.WebConstant.REQUEST_ID_PATTERN;
import static com.edu.ulab.app.web.constant.WebConstant.RQID;

@Slf4j
@RestController
@RequestMapping(value = WebConstant.VERSION_URL + "/user",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    private final UserDataFacade userDataFacade;

    public UserController(UserDataFacade userDataFacade) {
        this.userDataFacade = userDataFacade;
    }

    @PostMapping(value = "/create")
    @Operation(summary = "Create user with book row.",
            responses = {
                    @ApiResponse(description = "Id of created user and ids of his created books",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserBookResponse.class)))})
    public UserBookResponse createUserWithBooks(@RequestBody UserBookRequest request,
                                                @RequestHeader(RQID) @Pattern(regexp = REQUEST_ID_PATTERN) final String requestId) {
        UserBookResponse response = userDataFacade.createUserWithBooks(request);
        log.info("Response with created user and his books: {}", response);
        return response;
    }


    @PutMapping(value = "/update/{userId}")
    @Operation(summary = "Update user info and add books to his book row.",
            responses = {
                    @ApiResponse(description = "Id of updated user and ids of his created books.",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserBookResponse.class)))})
    public UserBookResponse updateUserWithBooks(@PathVariable Long userId, @RequestBody UserBookRequest request) throws Throwable {
        UserBookResponse response = userDataFacade.updateUserWithBooks(userId, request);
        log.info("Response with updated user and his books: {}", response);
        return response;
    }


    @Operation(summary = "Get user.",
            responses = {
                    @ApiResponse(description = "User info without books.",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserResponse.class)))})
    @GetMapping(value = "/get/{userId}")
    public UserResponse getUser(@PathVariable Long userId) {
        UserResponse response = userDataFacade.getUser(userId);
        log.info("Response with user: {}", response);
        return response;
    }

    @Operation(summary = "Get user and his books.",
            responses = {
                    @ApiResponse(description = "User info and his books.",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserBookExtendedResponse.class)))})
    @GetMapping(value = "/getUserWithBooks/{userId}")
    public UserBookExtendedResponse getUserWithBooks(@PathVariable Long userId) {
        UserBookExtendedResponse userBookResponse = userDataFacade.getUserWithBooks(userId);
        log.info("Response with user and his books: {}", userBookResponse);
        return userBookResponse;
    }

    @Operation(summary = "Delete user and his book row",
            responses = {
                    @ApiResponse(description = "Empty response.",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserBookResponse.class)))})
    @DeleteMapping(value = "/delete/{userId}")
    public void deleteUserWithBooks(@PathVariable Long userId) {
        log.info("Delete user and his books:  userId {}", userId);
        userDataFacade.deleteUserWithBooks(userId);
    }
}
