package com.edu.ulab.app.mapper;

import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.UserE;
import com.edu.ulab.app.web.request.UserRequest;
import com.edu.ulab.app.web.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto userRequestToUserDto(UserRequest userRequest);

    UserRequest userDtoToUserRequest(UserDto userDto);

    UserResponse userDtoToUserResponse(UserDto userDto);

    UserE userDtoToUserE(UserDto userDto);

    UserDto userEToUserDto(UserE userE);

    void updateUserEFromUserDto(UserDto userDto, @MappingTarget UserE userEForUpdate);

    @Mapping(ignore = true, target = "id")
    UserE createUserEFromUserDto(UserDto userDto);
}
